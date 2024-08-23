package com.ztech.order.model.entity

import com.ztech.order.model.common.Measure
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "product", uniqueConstraints = [
        UniqueConstraint(name = "unicst_name_measure_size", columnNames = ["name", "measure", "size"])
    ]
)
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id") val productId: Int? = null
) {

    @Column(name = "name", length = 64, nullable = false, updatable = false)
    lateinit var name: String

    @Column(name = "category", length = 64, nullable = false, updatable = false)
    lateinit var category: String

    @Enumerated(EnumType.STRING)
    @Column(name = "measure", nullable = false, updatable = false)
    lateinit var measure: Measure

    @Column(name = "size", precision = 10, scale = 2, nullable = false, updatable = false)
    lateinit var size: BigDecimal
}

