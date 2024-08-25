package com.ztech.order.model.domain

import java.time.LocalDateTime

data class Tracker(
    val id: Int,
    val status: String,
    val date: LocalDateTime,
)