package com.ztech.order.model.entity

import com.ztech.order.model.common.TrackerType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "purchase_tracker", uniqueConstraints = [
        UniqueConstraint(name = "unicst_purchaseitem_status", columnNames = ["purchase_item_id", "status"])
    ]
)
data class Tracker(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracker_id")
    val id: Int? = null,
    @Column(name = "date", nullable = false, updatable = false)
    val date: LocalDateTime = LocalDateTime.now()
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_item_id", nullable = false, updatable = false)
    lateinit var purchaseItem: PurchaseItem

    @Column(name = "status", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    lateinit var status: TrackerType
}


