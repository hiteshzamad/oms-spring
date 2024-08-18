package com.ztech.order.model.domain

data class Cart(
    val cartId: Int,
    val customerId: Int,
    val inventory: Inventory?,
    val quantity: Int
)
