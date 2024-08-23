package com.ztech.order.model.domain

data class Order(
    val orderId: Int,
    val customerId: Int,
    val orderAddress: OrderAddress,
    val orderPayment: OrderPayment,
    val orderItems: List<OrderItem>,
    val orderStatuses: List<OrderStatus>
)