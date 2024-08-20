package com.ztech.order.model.domain

data class OrderItem(
    val orderItemId: Int,
    val price: Double,
    val quantity: Int,
    val product: Product,
    val seller: Seller,
)
