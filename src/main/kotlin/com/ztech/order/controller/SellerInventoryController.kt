package com.ztech.order.controller

import com.ztech.order.model.dto.InventoryCreateRequest
import com.ztech.order.model.dto.InventoryUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
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
    ): ResponseEntity<Response> {
        val (productId, price, quantity) = inventory
        val response = inventoryService.createInventory(sellerId, productId, quantity, price)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getInventories(
        @PathVariable sellerId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Response> {
        val response = inventoryService.getInventoriesBySellerId(sellerId, page, pageSize)
        return responseSuccess(mapOf("inventories" to response.map { it.toMap() }))
    }

    @GetMapping("/{inventoryId}")
    fun getInventory(
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int
    ): ResponseEntity<Response> {
        val response = inventoryService.getInventoryBySellerIdAndInventoryId(sellerId, inventoryId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{inventoryId}")
    fun updateInventory(
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int,
        @RequestBody inventory: InventoryUpdateRequest
    ): ResponseEntity<Response> {
        val (price, quantityChange) = inventory
        inventoryService.updateInventoryByInventoryIdAndSellerId(sellerId, inventoryId, quantityChange, price)
        return responseSuccess()
    }

    @DeleteMapping("/{inventoryId}")
    fun deleteInventory(
        @PathVariable sellerId: Int,
        @PathVariable inventoryId: Int,
    ): ResponseEntity<Response> {
        inventoryService.deleteInventory(sellerId, inventoryId)
        return responseSuccess()
    }

}