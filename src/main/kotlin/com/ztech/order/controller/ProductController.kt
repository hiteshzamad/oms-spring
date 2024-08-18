package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
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
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "productId" to data!!.productId,
                    "name" to data.name,
                    "category" to data.category,
                    "measure" to data.measure,
                    "size" to data.size
                )
            ) else responseEntity(status)
        }
    }

    @GetMapping
    fun getProducts(
        @RequestParam(defaultValue = "") name: String,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "0") page: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = productService.getProductsByName(name, page, pageSize)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(status, mapOf("products" to data!!.map {
                mapOf(
                    "productId" to it.productId,
                    "name" to it.name,
                    "category" to it.category,
                    "measure" to it.measure,
                    "size" to it.size
                )
            })) else responseEntity(status)
        }
    }

    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable productId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = productService.getProduct(productId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "productId" to data!!.productId,
                    "name" to data.name,
                    "category" to data.category,
                    "measure" to data.measure,
                    "size" to data.size
                )
            ) else responseEntity(status)
        }
    }
}