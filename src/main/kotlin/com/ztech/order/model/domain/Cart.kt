package com.ztech.order.model.domain

data class Cart(
    val cartId: Int,
    val quantity: Int,
    val inventory: Inventory?,
)
