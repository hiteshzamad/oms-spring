package com.ztech.order.controller

import com.ztech.order.model.dto.CartCreateRequest
import com.ztech.order.model.dto.CartUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
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
    ): ResponseEntity<Response> {
        val (inventoryId, quantity) = cart
        val response = cartService.createCart(customerId, inventoryId, quantity)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getCarts(
        @PathVariable customerId: Int,
    ): ResponseEntity<Response> {
        val response = cartService.getCartsByCustomerId(customerId)
        return responseSuccess(mapOf("carts" to response.map { it.toMap() }))
    }

    @GetMapping("/{cartId}")
    fun getCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int
    ): ResponseEntity<Response> {
        val response = cartService.getCartByCartIdAndCustomerId(cartId, customerId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{cartId}")
    fun updateCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int,
        @RequestBody cart: CartUpdateRequest
    ): ResponseEntity<Response> {
        val (quantityChange) = cart
        cartService.updateCartByCartIdAndCustomerId(cartId, customerId, quantityChange)
        return responseSuccess()
    }

    @DeleteMapping("/{cartId}")
    fun deleteCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int,
    ): ResponseEntity<Response> {
        cartService.deleteCartByCartIdAndCustomerId(cartId, customerId)
        return responseSuccess()
    }

}
