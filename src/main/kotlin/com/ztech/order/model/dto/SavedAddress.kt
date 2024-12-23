package com.ztech.order.model.dto

import com.ztech.order.validator.ValidName

data class SavedAddressCreateRequest(
    @field:ValidName
    val name: String,
    val mobile: String,
    val address1: String,
    val address2: String?,
    val address3: String?,
    val city: String,
    val state: String,
    val country: String,
    val pincode: String
)

data class SavedAddressUpdateRequest(
    val name: String,
    val mobile: String,
    val address1: String,
    val address2: String?,
    val address3: String?,
    val city: String,
    val state: String,
    val country: String,
    val pincode: String
)