package com.ztech.order.model.dto

import jakarta.validation.constraints.Min

data class ProductCreateRequest(
    val name: String,
    val category: String,
    val measure: String,
    @field:Min(1, message = "Size should be greater than 0")
    val size: Double,
)