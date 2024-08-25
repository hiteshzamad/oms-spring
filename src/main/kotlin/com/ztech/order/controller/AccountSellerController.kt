package com.ztech.order.controller

import com.ztech.order.model.dto.SellerCreateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
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
    ): ResponseEntity<Response> {
        val (name) = seller
        val response = sellerService.createSeller(accountId, name)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getSellers(
        @PathVariable accountId: Int,
    ): ResponseEntity<Response> {
        val response = sellerService.getSellerByAccountId(accountId)
        return responseSuccess(response.toMap())
    }

    @GetMapping("/{sellerId}")
    fun getSeller(
        @PathVariable sellerId: Int
    ): ResponseEntity<Response> {
        val response = sellerService.getSellerBySellerId(sellerId)
        return responseSuccess(response.toMap())
    }

}