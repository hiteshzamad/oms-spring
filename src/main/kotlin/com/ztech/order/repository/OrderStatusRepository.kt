package com.ztech.order.repository

import com.ztech.order.model.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderStatusRepository : JpaRepository<OrderStatus, Int>{
    fun findByOrderStatusId(statusId: Int): OrderStatus
    fun findByOrderOrderId(orderId: Int): OrderStatus
    fun findByOrderOrderIdAndOrderStatusId(orderId: Int, statusId: Int): OrderStatus
    fun deleteByOrderOrderIdAndOrderStatusId(orderId: Int, statusId: Int)
}