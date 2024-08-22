package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.model.dto.OrderCreateRequest
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
    ): ResponseEntity<ControllerResponse> {
        val (savedAddressId, paymentMethod) = order
        val response = checkoutService.createOrder(customerId, savedAddressId, paymentMethod)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping
    fun getOrders(
        @PathVariable customerId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = orderService.getOrders(customerId)
        return responseEntity(response.status, mapOf("orders" to response.data?.map { it.toMap() }), response.message)
    }
}