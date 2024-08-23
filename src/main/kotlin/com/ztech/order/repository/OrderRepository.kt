package com.ztech.order.repository

import com.ztech.order.model.common.OrderStatusType
import com.ztech.order.model.entity.Order
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Int> {

    @EntityGraph("Order.address_payment_items_statuses")
    fun findByCustomerCustomerId(customerId: Int): List<Order>

    @EntityGraph("Order.all")
    fun findByOrderStatusesStatusAndOrderStatusesDateLessThan(
        status: OrderStatusType,
        date: java.time.LocalDateTime
    ): List<Order>
}
