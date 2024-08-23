package com.ztech.order.repository

import com.ztech.order.model.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderStatusRepository : JpaRepository<OrderStatus, Int> {

    @Modifying
    @Query("DELETE FROM OrderStatus os WHERE os.order.id = :orderId")
    fun deleteByOrderId(orderId: Int)
}