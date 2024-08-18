package com.ztech.order.model.domain

data class Product(
    val productId: Int,
    val name: String,
    val category: String,
    val measure: String,
    val size: Double
)