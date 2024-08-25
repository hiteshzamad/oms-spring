package com.ztech.order.model.domain

data class PurchaseItem(
    val id: Int,
    val price: Double,
    val quantity: Int,
    val product: Product?,
    val seller: Seller?,
    val trackers: List<Tracker>?
)
