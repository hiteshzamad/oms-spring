package com.ztech.order.model.domain

import java.time.LocalDateTime

data class OrderItemStatus(
    val orderItemStatusId: Int,
    val status: String,
    val date: LocalDateTime,
)