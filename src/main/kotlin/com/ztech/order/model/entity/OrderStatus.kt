package com.ztech.order.model.entity

import com.ztech.order.model.common.OrderStatusType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "order_status", uniqueConstraints = [
        UniqueConstraint(name = "unicst_order_id_status", columnNames = ["order_id", "status"])
    ]

)
data class OrderStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    val orderStatusId: Int? = null,
    @Column(name = "date", nullable = false, updatable = false)
    val date: LocalDateTime = LocalDateTime.now()
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    lateinit var order: Order

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    lateinit var status: OrderStatusType
}


