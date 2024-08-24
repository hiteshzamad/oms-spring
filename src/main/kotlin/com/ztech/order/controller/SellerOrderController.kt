package com.ztech.order.controller

import com.ztech.order.model.dto.OrderCreateRequest
import com.ztech.order.model.dto.OrderItemStatusUpdateRequest
import com.ztech.order.model.dto.OrderUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.service.CheckoutServiceImpl
import com.ztech.order.service.OrderServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sellers/{sellerId}/orders")
class SellerOrderController(
    private val checkoutService: CheckoutServiceImpl,
    private val orderService: OrderServiceImpl
) {

    @GetMapping
    fun getOrders(
        @PathVariable sellerId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrderBySellerId(sellerId)
        return responseSuccess(mapOf("orders" to response.map { it.toMap() }))
    }

    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable sellerId: Int,
        @PathVariable orderId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrderBySellerIdAndOrderId(sellerId, orderId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{orderId}")
    fun updateOrder(
        @PathVariable sellerId: Int,
        @PathVariable orderId: Int,
        @RequestBody orderItem: OrderItemStatusUpdateRequest
    ): ResponseEntity<Response> {
        val (orderItemId, status) = orderItem
        orderService.updateOrderItemStatusBySellerIdAndOrderItemId(sellerId, orderItemId, status)
        return responseSuccess()
    }

}