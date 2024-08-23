package com.ztech.order.repository

import com.ztech.order.model.entity.OrderPayment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderPaymentRepository : JpaRepository<OrderPayment, Int> {

    @Modifying
    @Query("DELETE FROM OrderPayment op WHERE op.order.id = :orderId")
    fun deleteByOrderId(orderId: Int)

}