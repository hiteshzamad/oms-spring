package com.ztech.order.model.entity

import jakarta.persistence.*

@Entity
@NamedEntityGraph(
    name = "Cart.inventory.product_seller",
    attributeNodes = [
        NamedAttributeNode("inventory", subgraph = "inventoryProductSeller")
    ],
    subgraphs = [
        NamedSubgraph(
            name = "inventoryProductSeller",
            attributeNodes = [NamedAttributeNode("product"), NamedAttributeNode("seller")]
        )
    ]
)
@Table(
    name = "cart", uniqueConstraints = [
        UniqueConstraint(name = "unicst_inventory_customer", columnNames = ["inventory_id", "customer_id"])
    ]
)
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    val cartId: Int? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    lateinit var customer: Customer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false, updatable = false)
    lateinit var inventory: Inventory

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0
}