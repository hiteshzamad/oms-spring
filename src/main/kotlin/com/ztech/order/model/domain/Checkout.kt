package com.ztech.order.model.domain

data class Checkout(
    val carts: List<Cart>,
    val totalAmount: Double,
)
