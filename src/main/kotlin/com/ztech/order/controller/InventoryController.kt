package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
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
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventoriesByProductName(name, page, pageSize)
        return responseEntity(response.status, mapOf("inventories" to response.data?.map { it.toMap() }), response.message)
    }

    @GetMapping("/{inventoryId}")
    fun getInventory(
        @PathVariable inventoryId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventoryByInventoryId(inventoryId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}