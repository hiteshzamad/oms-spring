package com.ztech.order.model.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "order_item", uniqueConstraints = [
        UniqueConstraint(name = "unicst_order_product_seller",columnNames = ["order_id", "product_id", "seller_id"])
    ]
)
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val orderItemId: Int? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    lateinit var order: Order

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    lateinit var product: Product

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false, updatable = false)
    lateinit var seller: Seller

    @Column(name = "price", precision = 10, scale = 2, nullable = false, updatable = false)
    lateinit var price: BigDecimal

    @Column(name = "quantity", nullable = false, updatable = false)
    var quantity: Int = 0
}
