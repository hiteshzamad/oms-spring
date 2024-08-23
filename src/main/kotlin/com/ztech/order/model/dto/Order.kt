package com.ztech.order.model.dto

data class OrderCreateRequest(
    val savedAddressId: Int,
    val paymentMethod: String,
)
