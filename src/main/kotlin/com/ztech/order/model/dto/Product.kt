package com.ztech.order.model.dto

data class ProductCreateRequest(
    val name: String,
    val category: String,
    val measure: String,
    val size: Double,
)