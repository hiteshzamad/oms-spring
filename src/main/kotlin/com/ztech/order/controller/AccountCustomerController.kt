package com.ztech.order.controller

import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.dto.CustomerCreateRequest
import com.ztech.order.model.toMap
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
    ): ResponseEntity<Response> {
        val (name) = customer
        val response = customerService.createCustomer(accountId, name)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getCustomers(
        @PathVariable accountId: Int,
    ): ResponseEntity<Response> {
        val response = customerService.getCustomerByAccountId(accountId)
        return responseSuccess(response.toMap())
    }

    @GetMapping("/{customerId}")
    fun getCustomer(
        @PathVariable customerId: Int
    ): ResponseEntity<Response> {
        val response = customerService.getCustomerByCustomerId(customerId)
        return responseSuccess(response.toMap())
    }

}