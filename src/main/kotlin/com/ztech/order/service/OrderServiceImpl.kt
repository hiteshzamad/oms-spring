package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.model.common.Measure
import com.ztech.order.model.common.OrderStatusType
import com.ztech.order.model.common.PaymentMethod
import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.model.toDomain
import com.ztech.order.repository.*
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.Cart as CartDomain
import com.ztech.order.model.domain.SavedAddress as SavedAddressDomain
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.Order as OrderEntity
import com.ztech.order.model.entity.OrderAddress as OrderAddressEntity
import com.ztech.order.model.entity.OrderItem as OrderItemEntity
import com.ztech.order.model.entity.OrderPayment as OrderPaymentEntity
import com.ztech.order.model.entity.OrderStatus as OrderStatusEntity
import com.ztech.order.model.entity.Product as ProductEntity
import com.ztech.order.model.entity.Seller as SellerEntity


@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderAddressRepository: OrderAddressRepository,
    private val orderStatusRepository: OrderStatusRepository,
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
        val orderStatuses = listOf(
            orderStatusRepository.save(OrderStatusEntity().also {
                it.order = order
                it.status = OrderStatusType.CREATED
            })
        )
        val orderItems = carts.map { cart ->
            orderItemRepository.save(OrderItemEntity().also { item ->
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
            })
        }
        val orderPayment = orderPaymentRepository.save(OrderPaymentEntity().also {
            it.order = order
            it.status = PaymentStatus.PENDING
            it.method = PaymentMethod.valueOf(paymentMethod.uppercase())
            it.amount = orderItems.sumOf { item -> (item.price * item.quantity.toBigDecimal()) }
        })
        order.also {
            it.orderItems = orderItems.toMutableSet()
            it.orderStatuses = orderStatuses.toMutableSet()
            it.orderPayment = orderPayment
            it.orderAddress = orderAddress
        }.toDomain()
    }

    fun getOrdersByCustomerId(customerId: Int) =
        orderRepository.findByCustomerCustomerId(customerId).map { it.toDomain() }

    fun getExpiredOrders() =
        orderRepository.findByOrderStatusesStatusAndOrderStatusesDateLessThan(
            OrderStatusType.CREATED,
            java.time.LocalDateTime.now().minusMinutes(5)
        ).map { it.toDomain() }

    fun deleteOrder(orderId: Int) = transactionHandler.execute {
        orderPaymentRepository.deleteByOrderId(orderId)
        orderItemRepository.deleteByOrderId(orderId)
        orderStatusRepository.deleteByOrderId(orderId)
        orderRepository.deleteById(orderId)
        orderAddressRepository.deleteByOrderId(orderId)
    }
}
