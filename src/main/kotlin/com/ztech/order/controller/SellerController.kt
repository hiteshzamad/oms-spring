package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
import com.ztech.order.core.responseEntity
import com.ztech.order.service.SellerServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sellers")
class SellerController(
    private val sellerService: SellerServiceImpl
) {

    @GetMapping
    fun getSellers(): ResponseEntity<ControllerResponse> {
        val response = sellerService.getSellers()
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(status, mapOf("sellers" to data!!.map {
                mapOf(
                    "sellerId" to it.sellerId,
                    "name" to it.name
                )
            })) else responseEntity(status)
        }
    }

    @GetMapping("/{sellerId}")
    fun getSeller(
        @PathVariable sellerId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = sellerService.getSellerBySellerId(sellerId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "sellerId" to data!!.sellerId,
                    "name" to data.name
                )
            ) else responseEntity(status)
        }
    }
}