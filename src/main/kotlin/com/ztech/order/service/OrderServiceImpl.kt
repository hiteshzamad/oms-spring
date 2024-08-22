package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.core.TransactionHandler
import com.ztech.order.repository.*
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.Order as OrderDomain


@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderAddressRepository: OrderAddressRepository,
    private val orderStatusRepository: OrderStatusRepository,
    private val orderItemRepository: OrderItemRepository,
    private val orderPaymentRepository: OrderPaymentRepository,
    private val transactionHandler: TransactionHandler
) : AbstractService() {

    fun getOrders(customerId: Int) = tryCatch {
        val orders = orderRepository.findByCustomerCustomerId(customerId)
        println(orders)
//        val payment = orderPaymentRepository.findByOrderOrderId(orders.get(0).orderId)
        ServiceResponse<List<OrderDomain>>(Status.SUCCESS, listOf())
    }
}