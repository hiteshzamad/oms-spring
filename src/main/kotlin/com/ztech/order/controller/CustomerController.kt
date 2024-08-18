package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
import com.ztech.order.core.responseEntity
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
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "customerId" to data!!.customerId,
                    "name" to data.name
                )
            ) else responseEntity(status)
        }
    }
}