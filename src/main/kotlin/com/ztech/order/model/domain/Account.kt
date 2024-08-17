package com.ztech.order.model.domain

import java.time.LocalDateTime

data class Account(
    val accountId: Int,
    val username: String,
    val password: String,
    val email: String?,
    val mobile: String?,
    val createdAt: LocalDateTime
)