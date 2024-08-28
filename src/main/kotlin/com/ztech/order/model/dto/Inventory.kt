package com.ztech.order.model.dto

import com.ztech.order.model.validator.ValidId
import jakarta.validation.constraints.Min

data class InventoryCreateRequest(
    @field:ValidId(message = "Invalid product id")
    val productId: Int,
    @field:Min(1, message = "Price should be greater than 0")
    val price: Double,
    @field:Min(1, message = "Quantity should be greater than 0")
    val quantity: Int
)

data class InventoryUpdateRequest(
    @field:Min(1, message = "Price should be greater than 0")
    val price: Double,
    val quantityChange: Int
)