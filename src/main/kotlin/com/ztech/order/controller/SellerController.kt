package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.service.SellerServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sellers")
class SellerController(
    private val sellerService: SellerServiceImpl
) {

    @GetMapping("/{sellerId}")
    fun getSeller(
        @PathVariable sellerId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = sellerService.getSellerBySellerId(sellerId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}