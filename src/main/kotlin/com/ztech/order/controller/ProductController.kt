package com.ztech.order.controller

import com.ztech.order.model.dto.ProductCreateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.model.validator.ValidId
import com.ztech.order.service.ProductServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductServiceImpl
) {

    @PostMapping
    fun createProduct(
        @RequestBody @Valid product: ProductCreateRequest
    ): ResponseEntity<Response> {
        val (name, category, measure, size) = product
        val response = productService.createProduct(name, category, measure, size)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "") name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Response> {
        val response = productService.getProductsByName(name, page, pageSize)
        return responseSuccess(mapOf("products" to response.map { it.toMap() }))
    }

    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable @ValidId productId: Int
    ): ResponseEntity<Response> {
        val response = productService.getProduct(productId)
        return responseSuccess(response.toMap())
    }

}