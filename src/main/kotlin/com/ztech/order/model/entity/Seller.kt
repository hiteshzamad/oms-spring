package com.ztech.order.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "seller")
data class Seller(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    val sellerId: Int? = null
) {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    lateinit var account: Account

    @Column(name = "name", nullable = false, length = 64)
    lateinit var name: String
}
