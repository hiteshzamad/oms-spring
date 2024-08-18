package com.ztech.order.model.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@NamedEntityGraph(
    name = "Inventory.product",
    attributeNodes = [NamedAttributeNode("product")]
)
@Table(
    name = "inventory", uniqueConstraints = [UniqueConstraint(columnNames = ["product_id", "seller_id"])]
)
data class Inventory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    val inventoryId: Int? = null
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    lateinit var product: Product

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    lateinit var seller: Seller

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    lateinit var price: BigDecimal

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0

}
