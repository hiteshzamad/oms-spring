package com.ztech.order.repository

import com.ztech.order.model.common.OrderItemStatusType
import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.model.entity.OrderItemStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderItemStatusRepository : JpaRepository<OrderItemStatus, Int> {

    @Modifying
    @Query("DELETE FROM OrderItemStatus os WHERE os.orderItem.orderItemId = :orderItemId")
    fun deleteByOrderItemId(@Param("orderItemId") orderItemId: Int)
}