package com.ztech.order.model.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "seller_inventory")
data class Inventory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    val inventoryId: Int? = null
) {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    lateinit var product: Product

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    lateinit var seller: Seller

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    lateinit var price: BigDecimal

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0
}
