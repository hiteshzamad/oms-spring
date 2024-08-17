package com.ztech.order.repository

import com.ztech.order.model.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Int>{
    fun findByOrderId(orderId: Int): Order
    fun findByCustomerCustomerId(customerId: Int): Order
    fun findByCustomerCustomerIdAndOrderId(customerId: Int, orderId: Int): Order
    fun deleteByCustomerCustomerIdAndOrderId(customerId: Int, orderId: Int)
}
