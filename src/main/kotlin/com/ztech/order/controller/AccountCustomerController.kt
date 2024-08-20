package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.model.domain.Customer
import com.ztech.order.model.dto.CustomerCreateRequest
import com.ztech.order.service.CustomerServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts/{accountId}/customers")
class AccountCustomerController(
    private val customerService: CustomerServiceImpl
) {

    @PostMapping
    fun createCustomer(
        @PathVariable accountId: Int,
        @RequestBody customer: CustomerCreateRequest
    ): ResponseEntity<ControllerResponse> {
        val (name) = customer
        val response = customerService.createCustomer(accountId, name)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping
    fun getCustomers(
        @PathVariable accountId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = customerService.getCustomerByAccountId(accountId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping("/{customerId}")
    fun getCustomer(
        @PathVariable customerId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = customerService.getCustomerByCustomerId(customerId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}