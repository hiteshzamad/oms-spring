package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.model.common.Measure
import com.ztech.order.model.common.OrderItemStatusType
import com.ztech.order.model.common.PaymentMethod
import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.model.toDomain
import com.ztech.order.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import com.ztech.order.model.domain.Cart as CartDomain
import com.ztech.order.model.domain.SavedAddress as SavedAddressDomain
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.Order as OrderEntity
import com.ztech.order.model.entity.OrderAddress as OrderAddressEntity
import com.ztech.order.model.entity.OrderItem as OrderItemEntity
import com.ztech.order.model.entity.OrderItemStatus as OrderItemStatusEntity
import com.ztech.order.model.entity.OrderPayment as OrderPaymentEntity
import com.ztech.order.model.entity.Product as ProductEntity
import com.ztech.order.model.entity.Seller as SellerEntity


@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderAddressRepository: OrderAddressRepository,
    private val orderItemStatusRepository: OrderItemStatusRepository,
    private val orderItemRepository: OrderItemRepository,
    private val orderPaymentRepository: OrderPaymentRepository,
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
        val orderAddress = orderAddressRepository.save(OrderAddressEntity().also {
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
        val orderItems = orderItemRepository.saveAll(
            carts.map { cart ->
                OrderItemEntity().also { item ->
                    item.order = order
                    item.quantity = cart.quantity
                    item.price = cart.inventory!!.price.toBigDecimal()
                    item.seller = SellerEntity(cart.inventory.seller!!.sellerId).also {
                        it.name = cart.inventory.seller.name
                    }
                    item.product = ProductEntity(cart.inventory.product!!.productId).also {
                        it.name = cart.inventory.product.name
                        it.category = cart.inventory.product.category
                        it.measure = Measure.valueOf(cart.inventory.product.measure.uppercase())
                        it.size = cart.inventory.product.size.toBigDecimal()
                    }
                }
            }
        )
        val orderItemStatuses = orderItemStatusRepository.saveAll(orderItems.map { item ->
            OrderItemStatusEntity().also {
                it.orderItem = item
                it.status = OrderItemStatusType.CREATED
            }
        }).groupBy { it.orderItem.orderItemId }
        val orderPayment = orderPaymentRepository.save(OrderPaymentEntity().also {
            it.order = order
            it.status = PaymentStatus.PENDING
            it.method = PaymentMethod.valueOf(paymentMethod.uppercase())
            it.amount = orderItems.sumOf { item -> (item.price * item.quantity.toBigDecimal()) }
        })
        order.also {
            it.orderItems.forEach { item ->
                item.statuses = orderItemStatuses.getOrDefault(item.orderItemId, emptyList()).toMutableSet()
            }
            it.orderItems = orderItems.toMutableSet()
            it.orderPayment = orderPayment
            it.orderAddress = orderAddress
        }.toDomain()
    }

    fun getOrdersByCustomerId(customerId: Int) =
        orderRepository.findByCustomerCustomerId(customerId).map { it.toDomain() }

    fun getOrderByOrderId(orderId: Int) =
        orderRepository.findByOrderId(orderId).toDomain()

    fun getOrderBySellerId(sellerId: Int) =
        orderRepository.findByOrderItemsSellerSellerIdAndOrderPaymentStatus(sellerId, PaymentStatus.SUCCESS)
            .map { it.toDomain(_payment = false, _seller = false) }

    fun getOrderBySellerIdAndOrderId(sellerId: Int, orderId: Int) =
        orderRepository.findByOrderItemsSellerSellerIdAndOrderIdAndOrderPaymentStatus(
            sellerId,
            orderId,
            PaymentStatus.SUCCESS
        )
            .toDomain(_payment = false, _seller = false)

    fun getExpiredOrders() =
        orderRepository.findByOrderPaymentStatusAndCreatedAtLessThan(
            PaymentStatus.PENDING,
            LocalDateTime.now().minusMinutes(5)
        ).map { it.toDomain() }


    fun updateOrderByOrderId(orderId: Int, transactionId: String) = transactionHandler.execute {
        orderPaymentRepository.updateTransactionIdByOrderId(
            orderId,
            transactionId,
            LocalDateTime.now(),
            PaymentStatus.SUCCESS
        )
    }

    fun updateOrderItemStatusBySellerIdAndOrderItemId(sellerId: Int, orderItemId: Int, status: String) =
        transactionHandler.execute {
            orderItemStatusRepository.save(OrderItemStatusEntity().also {
                it.orderItem = OrderItemEntity(orderItemId)
                it.status = OrderItemStatusType.valueOf(status.uppercase())
            })
        }

    fun deleteOrder(orderId: Int, orderItemIds: List<Int>) = transactionHandler.execute {
        orderPaymentRepository.deleteByOrderId(orderId)
        orderItemIds.forEach { orderItemId ->
            orderItemStatusRepository.deleteByOrderItemId(orderItemId)
        }
        orderItemRepository.deleteByOrderId(orderId)
        orderRepository.deleteById(orderId)
        orderAddressRepository.deleteByOrderId(orderId)
    }
}
