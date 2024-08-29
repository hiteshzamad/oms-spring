package com.ztech.order.model.dto

import com.ztech.order.validator.ValidName

data class CustomerCreateRequest(
    @field:ValidName
    val name: String
)
