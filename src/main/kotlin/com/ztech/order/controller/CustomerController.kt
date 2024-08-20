package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.model.domain.Customer
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
    ): ResponseEntity<ControllerResponse> {
        val response = customerService.getCustomerByCustomerId(customerId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}