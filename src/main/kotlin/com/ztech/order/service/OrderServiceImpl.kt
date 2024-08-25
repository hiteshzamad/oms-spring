package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.exception.RequestInvalidException
import com.ztech.order.exception.ResourceNotFoundException
import com.ztech.order.model.common.Measure
import com.ztech.order.model.common.PaymentMethod
import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.model.common.TrackerType
import com.ztech.order.model.toDomain
import com.ztech.order.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrElse
import com.ztech.order.model.domain.Cart as CartDomain
import com.ztech.order.model.domain.SavedAddress as SavedAddressDomain
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.DeliveryAddress as DeliveryAddressEntity
import com.ztech.order.model.entity.Order as OrderEntity
import com.ztech.order.model.entity.Payment as OrderPaymentEntity
import com.ztech.order.model.entity.Product as ProductEntity
import com.ztech.order.model.entity.PurchaseItem as PurchaseItemEntity
import com.ztech.order.model.entity.Seller as SellerEntity
import com.ztech.order.model.entity.Tracker as PurchaseItemStatusEntity


@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val deliveryAddressRepository: DeliveryAddressRepository,
    private val trackerRepository: TrackerRepository,
    private val purchaseItemRepository: PurchaseItemRepository,
    private val paymentRepository: PaymentRepository,
    private val transactionHandler: TransactionHandler
) {

    fun createOrder(
        customerId: Int,
        paymentMethod: String,
        savedAddress: SavedAddressDomain,
        carts: List<CartDomain>
    ) = transactionHandler.execute {
        val order = orderRepository.save(OrderEntity().also {
            it.customer = CustomerEntity(customerId)
        })
        val orderAddress = deliveryAddressRepository.save(DeliveryAddressEntity().also {
            it.order = order
            it.name = savedAddress.name
            it.mobile = savedAddress.mobile
            it.address1 = savedAddress.address1
            it.address2 = savedAddress.address2
            it.address3 = savedAddress.address3
            it.city = savedAddress.city
            it.state = savedAddress.state
            it.country = savedAddress.country
            it.pincode = savedAddress.pincode
        })
        val purchaseItems = purchaseItemRepository.saveAll(
            carts.map { cart ->
                PurchaseItemEntity().also { item ->
                    item.order = order
                    item.quantity = cart.quantity
                    item.price = cart.inventory!!.price.toBigDecimal()
                    item.seller = SellerEntity(cart.inventory.seller!!.id).also {
                        it.name = cart.inventory.seller.name
                    }
                    item.product = ProductEntity(cart.inventory.product!!.id).also {
                        it.name = cart.inventory.product.name
                        it.category = cart.inventory.product.category
                        it.measure = Measure.valueOf(cart.inventory.product.measure.uppercase())
                        it.size = cart.inventory.product.size.toBigDecimal()
                    }
                }
            }
        )
        val purchaseItemStatuses = trackerRepository.saveAll(purchaseItems.map { item ->
            PurchaseItemStatusEntity().also {
                it.purchaseItem = item
                it.status = TrackerType.CREATED
            }
        }).groupBy { it.purchaseItem.id }
        val orderPayment = paymentRepository.save(OrderPaymentEntity().also {
            it.order = order
            it.status = PaymentStatus.PENDING
            it.method = PaymentMethod.valueOf(paymentMethod.uppercase())
            it.amount = purchaseItems.sumOf { item -> (item.price * item.quantity.toBigDecimal()) }
        })
        order.also {
            it.address = orderAddress
            it.purchaseItems = purchaseItems.toMutableSet()
            it.purchaseItems.forEach { item ->
                item.trackers = purchaseItemStatuses.getOrDefault(item.id, emptyList()).toMutableSet()
            }
            it.payment = orderPayment
        }.toDomain()
    }

    fun getOrdersByCustomerId(customerId: Int) =
        orderRepository.findByCustomerId(customerId)
            .map { it.toDomain(_payment = false, _seller = false, _address = false, _tracker = false) }

    fun getOrderByCustomerIdAndOrderId(customerId: Int, orderId: Int) =
        orderRepository.findByIdAndCustomerId(orderId, customerId).getOrElse {
            throw ResourceNotFoundException("Order not found")
        }.toDomain()

    fun getOrderBySellerId(sellerId: Int) =
        orderRepository.findByPurchaseItemsSellerIdAndPaymentStatus(sellerId, PaymentStatus.SUCCESS)
            .map { it.toDomain(_payment = false, _seller = false, _address = false, _tracker = false) }

    fun getOrderBySellerIdAndOrderId(sellerId: Int, orderId: Int) =
        orderRepository.findByIdAndPurchaseItemsSellerIdAndPaymentStatus(
            orderId,
            sellerId,
            PaymentStatus.SUCCESS
        ).getOrElse {
            throw ResourceNotFoundException("Order not found")
        }.toDomain(_payment = false, _seller = false)

    fun getExpiredOrders() =
        orderRepository.findByCreatedAtLessThanAndPaymentStatus(
            LocalDateTime.now().minusMinutes(5),
            PaymentStatus.PENDING
        ).map { it.toDomain(_payment = false, _address = false, _tracker = false) }

    fun updateOrderByCustomerIdAndOrderId(
        customerId: Int, orderId: Int, transactionId: String
    ) = transactionHandler.execute {
        paymentRepository.updateByOrderIdAndCustomerId(
            orderId,
            customerId,
            transactionId,
            LocalDateTime.now(),
            PaymentStatus.SUCCESS
        )
    }

    fun updatePurchaseItemStatusBySellerIdAndPurchaseItemId(
        sellerId: Int, purchaseItemId: Int, status: String
    ) = transactionHandler.execute {
        val order = orderRepository.findByPurchaseItemsIdAndPurchaseItemsSellerIdAndPaymentStatus(
            purchaseItemId,
            sellerId,
            PaymentStatus.SUCCESS
        ).getOrElse {
            throw ResourceNotFoundException("Order not found")
        }
        val updateStatus = TrackerType.valueOf(status.uppercase())
        val currentStatus = order.purchaseItems.first().trackers.maxByOrNull { it.date }!!.status
        val isValidTransition = when (currentStatus) {
            TrackerType.CREATED -> updateStatus == TrackerType.CONFIRMED
            TrackerType.CONFIRMED -> updateStatus == TrackerType.DISPATCHED
            TrackerType.DISPATCHED -> updateStatus == TrackerType.DELIVERED
            else -> currentStatus != TrackerType.DELIVERED
        }
        if (isValidTransition) {
            trackerRepository.save(PurchaseItemStatusEntity().also {
                it.purchaseItem = PurchaseItemEntity(purchaseItemId)
                it.status = TrackerType.valueOf(status.uppercase())
            })
        } else {
            throw RequestInvalidException("Invalid tracker update data")
        }
    }

    fun deleteOrder(orderId: Int, purchaseItemIds: List<Int>) = transactionHandler.execute {
        paymentRepository.deleteByOrderId(orderId)
        trackerRepository.deleteByPurchaseItemId(purchaseItemIds)
        purchaseItemRepository.deleteAllByIdInBatch(purchaseItemIds)
        deliveryAddressRepository.deleteByOrderId(orderId)
        orderRepository.deleteById(orderId)
    }
}
