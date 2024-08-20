package com.ztech.order.model.domain

class OrderPayment(
    val paymentId: Int,
    val method: String,
    val status: String,
    val amount: Double,
)
