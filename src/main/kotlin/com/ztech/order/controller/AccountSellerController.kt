package com.ztech.order.controller

import com.ztech.order.model.dto.SellerCreateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.model.validator.ValidId
import com.ztech.order.service.SellerServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts/{accountId}/sellers")
@PreAuthorize("#accountId == authentication.principal.aid")
class AccountSellerController(
    private val sellerService: SellerServiceImpl
) {

    @PostMapping
    fun createSeller(
        @PathVariable @ValidId accountId: Int,
        @RequestBody @Valid seller: SellerCreateRequest
    ): ResponseEntity<Response> {
        val (name) = seller
        val response = sellerService.createSeller(accountId, name)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getSellers(
        @PathVariable @ValidId accountId: Int,
    ): ResponseEntity<Response> {
        val response = sellerService.getSellerByAccountId(accountId)
        return responseSuccess(response.toMap())
    }

    @GetMapping("/{sellerId}")
    @PreAuthorize("#sellerId == authentication.principal.sid")
    fun getSeller(
        @PathVariable @ValidId sellerId: Int
    ): ResponseEntity<Response> {
        val response = sellerService.getSellerBySellerId(sellerId)
        return responseSuccess(response.toMap())
    }

}