package com.ztech.order.model.domain

import com.ztech.order.model.common.Measure

data class Product(
    val name: String,
    val category: String,
    val measure: Measure,
    val size: Double
)