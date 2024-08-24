package com.ztech.order.repository

import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.model.entity.OrderPayment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OrderPaymentRepository : JpaRepository<OrderPayment, Int> {

    @Modifying
    @Query("UPDATE OrderPayment op " +
            "SET transactionId =:transactionId, " +
            "status = :status, " +
            "transactionDate =:transactionDate " +
            "WHERE op.order.id = :orderId")
    fun updateTransactionIdByOrderId(
        @Param("orderId") orderId: Int,
        @Param("transactionId") transactionId: String,
        @Param("transactionDate") transactionDate: LocalDateTime,
        @Param("status") status: PaymentStatus
    )

    @Modifying
    @Query("DELETE FROM OrderPayment op WHERE op.order.id = :orderId")
    fun deleteByOrderId(orderId: Int)

}