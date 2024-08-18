package com.ztech.order.model.entity

import com.ztech.order.model.common.Measure
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "product", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "measure", "size"])]
)
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id") val productId: Int? = null
) {

    @Column(name = "name", nullable = false, length = 64)
    lateinit var name: String

    @Column(name = "category", nullable = false, length = 64)
    lateinit var category: String

    @Enumerated(EnumType.STRING)
    @Column(name = "measure", nullable = false)
    lateinit var measure: Measure

    @Column(name = "size", nullable = false, precision = 10, scale = 2)
    lateinit var size: BigDecimal
}

