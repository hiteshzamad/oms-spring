package com.ztech.order.model.entity

import com.ztech.order.model.common.PaymentMethod
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "order_payment")
data class OrderPayment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_payment_id")
    val orderPaymentId: Int? = null,

    @Column(name = "transaction_date", nullable = false)
    val transactionDate: LocalDateTime = LocalDateTime.now()
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    lateinit var order: Order

    @Column(name = "method", nullable = false)
    @Enumerated(EnumType.STRING)
    lateinit var method: PaymentMethod

    @Column(name = "transaction_id", length = 62)
    var transactionId: String? = null

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    lateinit var amount: BigDecimal

}

