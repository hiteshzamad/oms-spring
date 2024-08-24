package com.ztech.order.model.entity

import com.ztech.order.model.common.OrderItemStatusType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "order_item_status", uniqueConstraints = [
        UniqueConstraint(name = "unicst_order_item_id_status", columnNames = ["order_item_id", "status"])
    ]
)
data class OrderItemStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_status_id")
    val orderItemStatusId: Int? = null,
    @Column(name = "date", nullable = false, updatable = false)
    val date: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false, updatable = false)
    lateinit var orderItem: OrderItem

    @Column(name = "status", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    lateinit var status: OrderItemStatusType
}


