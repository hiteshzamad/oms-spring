package com.ztech.order.repository

import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.model.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PaymentRepository : JpaRepository<Payment, Int> {

    @Modifying
    @Query(
        "UPDATE Payment p " +
                "SET transactionId =:transactionId, date = :date, status = :status " +
                "WHERE p.order.id = :orderId AND p.order.customer.id = :customerId"
    )
    fun updateByOrderIdAndCustomerId(
        @Param("orderId") orderId: Int,
        @Param("customerId") customerId: Int,
        @Param("transactionId") transactionId: String,
        @Param("date") date: LocalDateTime,
        @Param("status") status: PaymentStatus
    )

    @Modifying
    @Query("DELETE FROM Payment p WHERE p.order.id = :orderId")
    fun deleteByOrderId(@Param("orderId") orderId: Int)

}