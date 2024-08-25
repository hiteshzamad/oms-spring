package com.ztech.order.model.domain

data class Order(
    val id: Int,
    val customerId: Int,
    val deliveryAddress: DeliveryAddress?,
    val payment: Payment?,
    val purchaseItems: List<PurchaseItem>?,
)