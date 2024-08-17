package com.ztech.order.repository

import com.ztech.order.model.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Int> {
    fun findByOrderItemId(itemId: Int): OrderItem
    fun findByOrderOrderId(orderId: Int): OrderItem
    fun findByOrderOrderIdAndOrderItemId(orderId: Int, itemId: Int): OrderItem
    fun deleteByOrderOrderIdAndOrderItemId(orderId: Int, itemId: Int)
}