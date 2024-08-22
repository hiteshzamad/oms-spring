package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.model.dto.ProductCreateRequest
import com.ztech.order.service.ProductServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductServiceImpl
) {

    @PostMapping
    fun createProduct(
        @RequestBody product: ProductCreateRequest
    ): ResponseEntity<ControllerResponse> {
        val (name, category, measure, size) = product
        val response = productService.createProduct(name, category, measure, size)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "") name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = productService.getProductsByName(name, page, pageSize)
        return responseEntity(response.status, mapOf("products" to response.data?.map { it.toMap() }), response.message)
    }

    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable productId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = productService.getProduct(productId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}