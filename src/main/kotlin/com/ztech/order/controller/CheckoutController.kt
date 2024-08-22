package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.service.CheckoutServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customers/{customerId}/checkout")
class CheckoutController(
    private val checkoutService: CheckoutServiceImpl,
) {

    @PostMapping
    fun createCheckout(
        @PathVariable customerId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = checkoutService.checkout(customerId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}