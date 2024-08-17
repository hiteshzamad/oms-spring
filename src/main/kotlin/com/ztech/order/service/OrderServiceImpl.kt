package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.repository.*
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderAddressRepository: OrderAddressRepository,
    private val orderStatusRepository: OrderStatusRepository,
    private val orderPaymentRepository: OrderPaymentRepository,
    private val orderItemRepository: OrderItemRepository
) : AbstractService() {
}