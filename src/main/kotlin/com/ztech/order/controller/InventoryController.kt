package com.ztech.order.controller

import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.model.validator.ValidId
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
    ): ResponseEntity<Response> {
        val response = inventoryService.getInventoriesByProductName(name, page, pageSize)
        return responseSuccess(mapOf("inventories" to response.map { it.toMap() }))
    }

    @GetMapping("/{inventoryId}")
    fun getInventory(
        @PathVariable @ValidId inventoryId: Int
    ): ResponseEntity<Response> {
        val response = inventoryService.getInventoryByInventoryId(inventoryId)
        return responseSuccess(response.toMap())
    }

}