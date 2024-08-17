package com.ztech.order.model.domain

import java.math.BigDecimal

data class OrderItem(
    val orderItemId: Int,
    val orderId: Int,
    val productId: Int,
    val sellerId: Int,
    val price: BigDecimal,
    val quantity: Int
)
