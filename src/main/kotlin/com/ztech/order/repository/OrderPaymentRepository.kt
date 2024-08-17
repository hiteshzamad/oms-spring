package com.ztech.order.repository

import com.ztech.order.model.entity.OrderPayment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderPaymentRepository : JpaRepository<OrderPayment, Int> {
    fun findByOrderPaymentId(paymentId: Int): OrderPayment
    fun findByOrderOrderId(orderId: Int): OrderPayment
    fun findByOrderOrderIdAndOrderPaymentId(orderId: Int, paymentId: Int): OrderPayment
    fun deleteByOrderOrderIdAndOrderPaymentId(orderId: Int, paymentId: Int)
}