package com.ztech.order.controller

import com.ztech.order.model.dto.InventoryCreateRequest
import com.ztech.order.model.dto.InventoryUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.model.validator.ValidId
import com.ztech.order.service.InventoryServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sellers/{sellerId}/inventories")
@PreAuthorize("#sellerId == authentication.principal.sid")
class SellerInventoryController(
    private val inventoryService: InventoryServiceImpl
) {

    @PostMapping
    fun createInventory(
        @PathVariable @ValidId sellerId: Int,
        @RequestBody @Valid inventory: InventoryCreateRequest
    ): ResponseEntity<Response> {
        val (productId, price, quantity) = inventory
        val response = inventoryService.createInventory(sellerId, productId, quantity, price)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getInventories(
        @PathVariable @ValidId sellerId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Response> {
        val response = inventoryService.getInventoriesBySellerId(sellerId, page, pageSize)
        return responseSuccess(mapOf("inventories" to response.map { it.toMap() }))
    }

    @GetMapping("/{inventoryId}")
    fun getInventory(
        @PathVariable @ValidId sellerId: Int,
        @PathVariable @ValidId inventoryId: Int
    ): ResponseEntity<Response> {
        val response = inventoryService.getInventoryBySellerIdAndInventoryId(sellerId, inventoryId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{inventoryId}")
    fun updateInventory(
        @PathVariable @ValidId sellerId: Int,
        @PathVariable @ValidId inventoryId: Int,
        @RequestBody @Valid inventory: InventoryUpdateRequest
    ): ResponseEntity<Response> {
        val (price, quantityChange) = inventory
        inventoryService.updateInventoryByInventoryIdAndSellerId(sellerId, inventoryId, quantityChange, price)
        return responseSuccess()
    }

    @DeleteMapping("/{inventoryId}")
    fun deleteInventory(
        @PathVariable @ValidId sellerId: Int,
        @PathVariable @ValidId inventoryId: Int,
    ): ResponseEntity<Response> {
        inventoryService.deleteInventory(sellerId, inventoryId)
        return responseSuccess()
    }

}