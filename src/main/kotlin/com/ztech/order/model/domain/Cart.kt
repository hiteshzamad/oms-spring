package com.ztech.order.model.domain

data class Cart(
    val cartId: Int,
    val customerId: Int,
    val inventoryId: Int,
    val quantity: Int
)
