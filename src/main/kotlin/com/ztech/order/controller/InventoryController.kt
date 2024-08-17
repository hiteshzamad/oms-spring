package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
import com.ztech.order.core.responseEntity
import com.ztech.order.service.InventoryServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/inventories")
class InventoryController(
    private val inventoryService: InventoryServiceImpl
) {

    @GetMapping
    fun getInventories(): ResponseEntity<ControllerResponse> {
        val response = inventoryService.getInventories()
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(status, mapOf("inventories" to data!!.map {
                mapOf(
                    "inventoryId" to it.inventoryId,
                    "sellerId" to it.sellerId,
                    "productId" to it.productId,
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
                    "productId" to data.productId,
                    "quantity" to data.quantity,
                    "price" to data.price
                )
            ) else responseEntity(status)
        }
    }
}