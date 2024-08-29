package com.ztech.order.controller

import com.ztech.order.model.dto.TrackerUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.validator.ValidId
import com.ztech.order.service.CheckoutServiceImpl
import com.ztech.order.service.OrderServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sellers/{sellerId}/orders")
@PreAuthorize("#sellerId == authentication.principal.sid")
class SellerOrderController(
    private val checkoutService: CheckoutServiceImpl,
    private val orderService: OrderServiceImpl
) {

    @GetMapping
    fun getOrders(
        @PathVariable @ValidId sellerId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrderBySellerId(sellerId)
        return responseSuccess(mapOf("orders" to response.map { it.toMap() }))
    }

    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable @ValidId sellerId: Int,
        @PathVariable @ValidId orderId: Int
    ): ResponseEntity<Response> {
        val response = orderService.getOrderBySellerIdAndOrderId(sellerId, orderId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{orderId}")
    fun updateOrder(
        @PathVariable @ValidId sellerId: Int,
        @PathVariable @ValidId orderId: Int,
        @RequestBody @Valid purchaseItem: TrackerUpdateRequest
    ): ResponseEntity<Response> {
        val (purchaseItemId, status) = purchaseItem
        orderService.updatePurchaseItemStatusBySellerIdAndPurchaseItemId(sellerId, purchaseItemId, status)
        return responseSuccess()
    }

}