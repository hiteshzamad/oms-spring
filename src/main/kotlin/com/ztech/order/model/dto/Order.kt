package com.ztech.order.model.dto

import com.ztech.order.model.validator.ValidId

data class OrderCreateRequest(
    @field:ValidId(message = "Invalid saved address id")
    val savedAddressId: Int,
    val paymentMethod: String,
)

data class OrderUpdateRequest(
    val transactionId: String
)

data class TrackerUpdateRequest(
    @field:ValidId(message = "Invalid purchase item id")
    val purchaseItemId: Int,
    val status: String
)