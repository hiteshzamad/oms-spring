package com.ztech.order.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "cart")
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    val cartId: Int? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    lateinit var customer: Customer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    lateinit var inventory: Inventory

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0
}