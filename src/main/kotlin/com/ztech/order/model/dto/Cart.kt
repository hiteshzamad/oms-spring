package com.ztech.order.model.dto

data class CartCreateRequest(
    val inventoryId: Int,
    val quantity: Int
)

data class CartUpdateRequest(
    val quantityChange: Int
)

