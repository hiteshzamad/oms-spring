package com.ztech.order.model.dto

import com.ztech.order.model.validator.ValidPassword
import com.ztech.order.model.validator.ValidUsername

data class AccountCreateRequest(
    @field:ValidUsername
    val username: String,
    @field:ValidPassword
    val password: String,
    val email: String? = null,
    val mobile: String? = null
)

data class AccountUpdateRequest(
    val email: String? = null,
    val mobile: String? = null,
    @field:ValidPassword
    val password: String? = null
)