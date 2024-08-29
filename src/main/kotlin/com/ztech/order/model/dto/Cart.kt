package com.ztech.order.model.dto

import com.ztech.order.validator.ValidId
import jakarta.validation.constraints.Min

data class CartCreateRequest(
    @field:ValidId(message = "Invalid inventory id")
    val inventoryId: Int,
    @field:Min(1, message = "Quantity should be greater than 0")
    val quantity: Int
)

data class CartUpdateRequest(
    val quantityChange: Int
)

