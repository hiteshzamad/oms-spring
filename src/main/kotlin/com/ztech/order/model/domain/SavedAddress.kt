package com.ztech.order.model.domain

data class SavedAddress(
    val addressId: Int,
    val customerId: Int,
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
