package com.ztech.order.model.dto

import com.ztech.order.model.validator.ValidName

data class CustomerCreateRequest(
    @field:ValidName
    val name: String
)
