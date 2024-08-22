package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
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
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping
    fun getInventories(
        @PathVariable sellerId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventoriesBySellerId(sellerId, page, pageSize)
        return responseEntity(response.status, mapOf("inventories" to response.data?.map { it.toMap() }), response.message)
    }

    @GetMapping("/{inventoryId}")
    fun getInventory(
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventoryBySellerIdAndInventoryId(sellerId, inventoryId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @PutMapping("/{inventoryId}")
    fun updateInventory(
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int,
        @RequestBody inventory: InventoryUpdateRequest
    ): ResponseEntity<ControllerResponse> {
        val (price, quantityChange) = inventory
        val response = inventoryService.updateInventory(sellerId, inventoryId, quantityChange, price)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @DeleteMapping("/{inventoryId}")
    fun deleteInventory(
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.deleteInventory(sellerId, inventoryId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}