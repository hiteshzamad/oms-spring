package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.model.domain.Seller
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
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping
    fun getSellers(
        @PathVariable accountId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = sellerService.getSellerByAccountId(accountId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping("/{sellerId}")
    fun getSeller(
        @PathVariable sellerId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = sellerService.getSellerBySellerId(sellerId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}