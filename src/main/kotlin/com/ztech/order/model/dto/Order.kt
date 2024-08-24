package com.ztech.order.model.dto

data class OrderCreateRequest(
    val savedAddressId: Int,
    val paymentMethod: String,
)

data class OrderUpdateRequest(
    val transactionId: String
)

data class OrderItemStatusUpdateRequest(
    val orderItemId: Int,
    val status: String
)