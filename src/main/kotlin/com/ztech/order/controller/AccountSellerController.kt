package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
import com.ztech.order.core.responseEntity
import com.ztech.order.model.dto.SellerCreateRequest
import com.ztech.order.service.SellerServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts/{accountId}/sellers")
class AccountSellerController(
    private val sellerService: SellerServiceImpl
) {

    @PostMapping
    fun createSeller(
        @PathVariable accountId: Int,
        @RequestBody seller: SellerCreateRequest
    ): ResponseEntity<ControllerResponse> {
        val (name) = seller
        val response = sellerService.createSeller(accountId, name)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "sellerId" to data!!.sellerId,
                    "name" to data.name
                )
            ) else responseEntity(status)
        }
    }

    @GetMapping
    fun getSellers(
        @PathVariable accountId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = sellerService.getSellerByAccountId(accountId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "sellerId" to data!!.sellerId,
                    "name" to data.name
                )
            ) else responseEntity(status)
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