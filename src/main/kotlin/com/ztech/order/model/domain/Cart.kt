package com.ztech.order.model.domain

data class Cart(
    val id: Int,
    val quantity: Int,
    val inventory: Inventory?,
)
