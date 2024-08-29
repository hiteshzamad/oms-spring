package com.ztech.order.model.dto

import com.ztech.order.validator.ValidName

data class SellerCreateRequest(
    @field:ValidName
    val name: String
)
