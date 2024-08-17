package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
import com.ztech.order.core.responseEntity
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
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "customerId" to data!!.customerId,
                    "name" to data.name
                )
            ) else responseEntity(status)
        }
    }

    @GetMapping
    fun getCustomers(
        @PathVariable accountId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = customerService.getCustomerByAccountId(accountId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "customerId" to data!!.customerId,
                    "name" to data.name
                )
            ) else responseEntity(status)
        }
    }

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