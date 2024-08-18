package com.ztech.order.model.domain

data class Inventory(
    val inventoryId: Int,
    val product: Product?,
    val sellerId: Int,
    val price: Double,
    val quantity: Int
)
