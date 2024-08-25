package com.ztech.order.model.entity

import com.ztech.order.model.common.PaymentMethod
import com.ztech.order.model.common.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(
    name = "payment",
    uniqueConstraints = [
        UniqueConstraint(name = "unicst_order", columnNames = ["order_id"])
    ]
)
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    val id: Int? = null,
) {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    lateinit var order: Order

    @Column(name = "method", nullable = false)
    @Enumerated(EnumType.STRING)
    lateinit var method: PaymentMethod

    @Column(name = "transaction_id", length = 64)
    var transactionId: String? = null

    @Column(name = "date")
    val date: LocalDateTime? = null

    @Column(name = "amount", precision = 10, scale = 2, nullable = false, updatable = false)
    lateinit var amount: BigDecimal

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    lateinit var status: PaymentStatus

}

