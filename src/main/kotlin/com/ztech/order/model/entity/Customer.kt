package com.ztech.order.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    val customerId: Int? = null
) {
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    lateinit var account: Account

    @Column(name = "name", nullable = false, length = 64)
    lateinit var name: String
}