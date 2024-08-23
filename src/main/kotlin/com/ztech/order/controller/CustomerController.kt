package com.ztech.order.controller

import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.service.CustomerServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerService: CustomerServiceImpl
) {

    @GetMapping("/{customerId}")
    fun getCustomer(
        @PathVariable customerId: Int
    ): ResponseEntity<Response> {
        val response = customerService.getCustomerByCustomerId(customerId)
        return responseSuccess(response.toMap())
    }

}