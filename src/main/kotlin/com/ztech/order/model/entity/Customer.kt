package com.ztech.order.model.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "customer",
    uniqueConstraints = [
        UniqueConstraint(name = "unicst_account_id", columnNames = ["account_id"])
    ]
)
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    val customerId: Int? = null
) {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    lateinit var account: Account

    @Column(name = "name", length = 64, nullable = false, updatable = false)
    lateinit var name: String
}