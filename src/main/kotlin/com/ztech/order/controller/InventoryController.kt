package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
import com.ztech.order.core.responseEntity
import com.ztech.order.service.InventoryServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inventories")
class InventoryController(
    private val inventoryService: InventoryServiceImpl
) {

    @GetMapping
    fun getInventories(
        @RequestParam(defaultValue = "") name: String,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "0") page: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventoriesByProductName(name, page, pageSize)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(status, mapOf("inventories" to data!!.map {
                mapOf(
                    "inventoryId" to it.inventoryId,
                    "sellerId" to it.sellerId,
                    "product" to it.product?.let { product ->
                        mapOf(
                            "productId" to product.productId,
                            "name" to product.name,
                            "category" to product.category,
                            "measure" to product.measure,
                            "size" to product.size
                        )
                    },
                    "quantity" to it.quantity,
                    "price" to it.price
                )
            })) else responseEntity(status)
        }
    }

    @GetMapping("/{inventoryId}")
    fun getInventory(
        @PathVariable inventoryId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventoryByInventoryId(inventoryId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "inventoryId" to data!!.inventoryId,
                    "sellerId" to data.sellerId,
                    "product" to data.product?.let { product ->
                        mapOf(
                            "productId" to product.productId,
                            "name" to product.name,
                            "category" to product.category,
                            "measure" to product.measure,
                            "size" to product.size
                        )
                    },
                    "quantity" to data.quantity,
                    "price" to data.price
                )
            ) else responseEntity(status)
        }
    }
}