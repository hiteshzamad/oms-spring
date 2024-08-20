package com.ztech.order.model.domain

data class Inventory(
    val inventoryId: Int,
    val price: Double,
    val quantity: Int,
    val product: Product?,
    val seller: Seller?,
)
