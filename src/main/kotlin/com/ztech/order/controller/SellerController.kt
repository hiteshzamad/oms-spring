package com.ztech.order.controller

import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.model.validator.ValidId
import com.ztech.order.service.SellerServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sellers")
class SellerController(
    private val sellerService: SellerServiceImpl
) {

    @GetMapping("/{sellerId}")
    @PreAuthorize("#sellerId == authentication.principal.sid")
    fun getSeller(
        @PathVariable @ValidId sellerId: Int
    ): ResponseEntity<Response> {
        val response = sellerService.getSellerBySellerId(sellerId)
        return responseSuccess(response.toMap())
    }

}