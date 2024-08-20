package com.ztech.order.model.domain

import java.time.LocalDateTime

data class OrderStatus(
    val orderStatusId: Int,
    var status: String,
    val date: LocalDateTime,
)