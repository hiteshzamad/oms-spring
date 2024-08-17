package com.ztech.order.model.dto

data class AccountCreateRequest(
    val username: String,
    val password: String,
    val email: String? = null,
    val mobile: String? = null
)

data class AccountUpdateRequest(
    val email: String? = null,
    val mobile: String? = null,
    val password: String? = null
)