package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.model.common.OrderStatusType
import com.ztech.order.model.common.PaymentMethod
import com.ztech.order.repository.*
import org.springframework.stereotype.Service
import java.math.BigDecimal
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
    private val orderPaymentRepository: OrderPaymentRepository
) : AbstractService() {

    fun createOrder(
        customerId: Int, paymentMethod: String, savedAddress: SavedAddressDomain, cartItems: List<CartDomain>
    ) = tryCatchDaoCall {
        val order = orderRepository.save(OrderEntity().also {
            it.customer = CustomerEntity(customerId)
        })
        val orderItemList = cartItems.map { cart ->
            orderItemRepository.save(OrderItemEntity().also {
                it.order = order
                it.quantity = cart.quantity
                it.price = cart.inventory!!.price.toBigDecimal()
                it.seller = SellerEntity(cart.inventory.seller!!.sellerId)
                it.product = ProductEntity(cart.inventory.product!!.productId)
            })
        }
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
        val orderStatus = orderStatusRepository.save(OrderStatusEntity().also {
            it.order = order
            it.status = OrderStatusType.CREATED
        })
        val orderPayment = orderPaymentRepository.save(OrderPaymentEntity().also {
            it.order = order
            it.method = PaymentMethod.valueOf(paymentMethod.uppercase())
            it.amount = orderItemList.map { orderItem -> (orderItem.price * orderItem.quantity.toBigDecimal()) }
                .reduce(BigDecimal::add)
        })
        ServiceResponse(Status.SUCCESS, order.toDomain(orderAddress, orderPayment, orderItemList, listOf(orderStatus)))
    }
}