package com.ztech.order.model.dto

data class InventoryCreateRequest(
    val productId: Int,
    val price: Double,
    val quantity: Int
)

data class InventoryUpdateRequest(
    val price: Double,
    val quantity: Int
)