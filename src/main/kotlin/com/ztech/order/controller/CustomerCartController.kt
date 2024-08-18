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
                    "inventory" to data.inventory?.let { inventory ->
                        mapOf(
                            "inventoryId" to inventory.inventoryId,
                            "sellerId" to inventory.sellerId,
                            "product" to inventory.product?.let { product ->
                                mapOf(
                                    "productId" to product.productId,
                                    "name" to product.name,
                                    "category" to product.category,
                                    "measure" to product.measure,
                                    "size" to product.size
                                )
                            },
                            "quantity" to inventory.quantity,
                            "price" to inventory.price
                        )
                    },
                    "quantity" to data.quantity,
                )
            ) else responseEntity(status)
        }
    }

    @GetMapping
    fun getCarts(
        @PathVariable customerId: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "0") page: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = cartService.getCartsByCustomerId(customerId, page, pageSize)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(status, mapOf("carts" to data!!.map {
                mapOf(
                    "cartId" to it.cartId,
                    "inventory" to it.inventory?.let { inventory ->
                        mapOf(
                            "inventoryId" to inventory.inventoryId,
                            "sellerId" to inventory.sellerId,
                            "product" to inventory.product?.let { product ->
                                mapOf(
                                    "productId" to product.productId,
                                    "name" to product.name,
                                    "category" to product.category,
                                    "measure" to product.measure,
                                    "size" to product.size
                                )
                            },
                            "quantity" to inventory.quantity,
                            "price" to inventory.price
                        )
                    },
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
                    "inventory" to data.inventory?.let { inventory ->
                        mapOf(
                            "inventoryId" to inventory.inventoryId,
                            "sellerId" to inventory.sellerId,
                            "product" to inventory.product?.let { product ->
                                mapOf(
                                    "productId" to product.productId,
                                    "name" to product.name,
                                    "category" to product.category,
                                    "measure" to product.measure,
                                    "size" to product.size
                                )
                            },
                            "quantity" to inventory.quantity,
                            "price" to inventory.price
                        )
                    },
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
                    "inventory" to data.inventory?.let { inventory ->
                        mapOf(
                            "inventoryId" to inventory.inventoryId,
                            "sellerId" to inventory.sellerId,
                            "product" to inventory.product?.let { product ->
                                mapOf(
                                    "productId" to product.productId,
                                    "name" to product.name,
                                    "category" to product.category,
                                    "measure" to product.measure,
                                    "size" to product.size
                                )
                            },
                            "quantity" to inventory.quantity,
                            "price" to inventory.price
                        )
                    },
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