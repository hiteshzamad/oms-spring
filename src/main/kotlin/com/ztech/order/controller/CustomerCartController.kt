package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.model.dto.CartCreateRequest
import com.ztech.order.model.dto.CartUpdateRequest
import com.ztech.order.service.CartServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers/{customerId}/carts")
class CustomerCartController(
    private val cartService: CartServiceImpl
) {

    @PostMapping
    fun createCart(
        @PathVariable customerId: Int,
        @RequestBody cart: CartCreateRequest
    ): ResponseEntity<ControllerResponse> {
        val (inventoryId, quantity) = cart
        val response = cartService.createCart(customerId, inventoryId, quantity)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping
    fun getCarts(
        @PathVariable customerId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = cartService.getCartsByCustomerId(customerId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping("/{cartId}")
    fun getCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = cartService.getCartByCustomerIdAndCartId(customerId, cartId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @PutMapping("/{cartId}")
    fun updateCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int,
        @RequestBody cart: CartUpdateRequest
    ): ResponseEntity<ControllerResponse> {
        val (quantity) = cart
        val response = cartService.updateCart(customerId, cartId, quantity)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @DeleteMapping("/{cartId}")
    fun deleteCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = cartService.deleteCart(customerId, cartId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}
