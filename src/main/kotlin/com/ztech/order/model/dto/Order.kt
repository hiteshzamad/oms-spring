package com.ztech.order.model.dto

data class OrderCreateRequest(
    val savedAddress: Int,
    val paymentMethod: String,
)

data class OrderUpdateRequest(
    val orderId: Int,
    val status: String,
)

data class OrderAddress(
    val name: String,
    val mobile: String,
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val state: String,
    val country: String,
    val pincode: String,
)