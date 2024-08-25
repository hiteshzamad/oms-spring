package com.ztech.order.model.domain

class Payment(
    val id: Int,
    val method: String,
    val status: String,
    val amount: Double,
)
