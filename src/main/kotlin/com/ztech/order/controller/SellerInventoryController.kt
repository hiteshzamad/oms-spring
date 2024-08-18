package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
import com.ztech.order.core.responseEntity
import com.ztech.order.model.dto.InventoryCreateRequest
import com.ztech.order.model.dto.InventoryUpdateRequest
import com.ztech.order.service.InventoryServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sellers/{sellerId}/inventories")
class SellerInventoryController(
    private val inventoryService: InventoryServiceImpl
) {

    @PostMapping
    fun createInventory(
        @PathVariable sellerId: Int,
        @RequestBody inventory: InventoryCreateRequest
    ): ResponseEntity<ControllerResponse> {
        val (productId, price, quantity) = inventory
        val response = inventoryService.createInventory(sellerId, productId, quantity, price)
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

    @GetMapping
    fun getInventories(
        @PathVariable sellerId: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "0") page: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventoriesBySellerId(sellerId, page, pageSize)
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
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventoryBySellerIdAndInventoryId(sellerId, inventoryId)
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

    @PutMapping("/{inventoryId}")
    fun updateInventory(
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int,
        @RequestBody inventory: InventoryUpdateRequest
    ): ResponseEntity<ControllerResponse> {
        val (price, quantity) = inventory
        val response = inventoryService.updateInventory(sellerId, inventoryId, quantity, price)
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

    @DeleteMapping("/{inventoryId}")
    fun deleteInventory(
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.deleteInventory(sellerId, inventoryId)
        with(response) {
            return responseEntity(status)
        }
    }
}