package com.ztech.order.controller

import com.ztech.order.model.dto.OrderCreateRequest
import com.ztech.order.model.dto.OrderUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.service.CheckoutServiceImpl
import com.ztech.order.service.OrderServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers/{customerId}/orders")
class CustomerOrderController(
    private val checkoutService: CheckoutServiceImpl,
    private val orderService: OrderServiceImpl
) {

    @PostMapping
    fun createOrder(
        @PathVariable customerId: Int,
        @RequestBody order: OrderCreateRequest
    ): ResponseEntity<Response> {
        val (savedAddressId, paymentMethod) = order
        val response = checkoutService.processOrder(customerId, savedAddressId, paymentMethod)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getOrders(
        @PathVariable customerId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrdersByCustomerId(customerId)
        return responseSuccess(mapOf("orders" to response.map { it.toMap() }))
    }

    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable customerId: Int,
        @PathVariable orderId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrderByCustomerIdAndOrderId(customerId, orderId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{orderId}")
    fun updateOrder(
        @PathVariable customerId: Int,
        @PathVariable orderId: Int,
        @RequestBody order: OrderUpdateRequest
    ): ResponseEntity<Response> {
        val (transactionId) = order
        orderService.updateOrderByCustomerIdAndOrderId(customerId, orderId, transactionId)
        return responseSuccess()
    }

}