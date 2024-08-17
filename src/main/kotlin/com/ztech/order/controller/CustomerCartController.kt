package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
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
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "cartId" to data!!.cartId,
                    "inventoryId" to data.inventoryId,
                    "quantity" to data.quantity,
                )
            ) else responseEntity(status)
        }
    }

    @GetMapping
    fun getCarts(
        @PathVariable customerId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = cartService.getCartsByCustomerId(customerId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(status, mapOf("carts" to data!!.map {
                mapOf(
                    "cartId" to it.cartId,
                    "inventoryId" to it.inventoryId,
                    "quantity" to it.quantity,
                )
            })) else responseEntity(status)
        }
    }

    @GetMapping("/{cartId}")
    fun getCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = cartService.getCartByCustomerIdAndCartId(customerId, cartId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "cartId" to data!!.cartId,
                    "inventoryId" to data.inventoryId,
                    "quantity" to data.quantity,
                )
            ) else responseEntity(status)
        }
    }

    @PutMapping("/{cartId}")
    fun updateCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int,
        @RequestBody cart: CartUpdateRequest
    ): ResponseEntity<ControllerResponse> {
        val (quantity) = cart
        val response = cartService.updateCart(customerId, cartId, quantity)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "cartId" to data!!.cartId,
                    "inventoryId" to data.inventoryId,
                    "quantity" to data.quantity,
                )
            ) else responseEntity(status)
        }
    }

    @DeleteMapping("/{cartId}")
    fun deleteCart(
        @PathVariable customerId: Int,
        @PathVariable cartId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = cartService.deleteCart(customerId, cartId)
        with(response) {
            return responseEntity(status)
        }
    }
}