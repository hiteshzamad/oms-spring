package com.ztech.order.repository

import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.model.entity.Order
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Int> {

    @EntityGraph("Order.address_payment_items")
    fun findByCustomerCustomerId(customerId: Int): List<Order>

    @EntityGraph("Order.all")
    fun findByOrderId(orderId: Int): Order

    @EntityGraph("Order.address_payment_items")
    fun findByOrderItemsSellerSellerIdAndOrderPaymentStatus(sellerId: Int, paymentStatus: PaymentStatus): List<Order>

    @EntityGraph("Order.all")
    fun findByOrderItemsSellerSellerIdAndOrderIdAndOrderPaymentStatus(
        sellerId: Int,
        orderId: Int,
        paymentStatus: PaymentStatus
    ): Order

    @EntityGraph("Order.all")
    fun findByOrderPaymentStatusAndCreatedAtLessThan(
        orderPaymentStatus: PaymentStatus,
        date: java.time.LocalDateTime
    ): List<Order>
}
