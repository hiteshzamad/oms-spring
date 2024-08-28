package com.ztech.order.controller

import com.ztech.order.model.dto.CustomerCreateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.model.validator.ValidId
import com.ztech.order.service.CustomerServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts/{accountId}/customers")
@PreAuthorize("#accountId == authentication.principal.aid")
class AccountCustomerController(
    private val customerService: CustomerServiceImpl
) {

    @PostMapping
    fun createCustomer(
        @PathVariable @ValidId accountId: Int,
        @RequestBody @Valid customer: CustomerCreateRequest
    ): ResponseEntity<Response> {
        val (name) = customer
        val response = customerService.createCustomer(accountId, name)
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getCustomers(
        @PathVariable @ValidId accountId: Int,
    ): ResponseEntity<Response> {
        val response = customerService.getCustomerByAccountId(accountId)
        return responseSuccess(response.toMap())
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("#customerId == authentication.principal.cid")
    fun getCustomer(
        @PathVariable @ValidId customerId: Int
    ): ResponseEntity<Response> {
        val response = customerService.getCustomerByCustomerId(customerId)
        return responseSuccess(response.toMap())
    }

}