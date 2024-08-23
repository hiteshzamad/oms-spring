package com.ztech.order.controller

import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
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
    ): ResponseEntity<Response> {
        val response = checkoutService.checkout(customerId)
        return responseSuccess(response.toMap())
    }

}