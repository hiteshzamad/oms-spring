package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.model.dto.SavedAddressCreateRequest
import com.ztech.order.model.dto.SavedAddressUpdateRequest
import com.ztech.order.service.SavedAddressServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers/{customerId}/savedAddresses")
class CustomerSavedAddressController(
    private val savedAddressService: SavedAddressServiceImpl
) {

    @PostMapping
    fun createSavedAddress(
        @PathVariable customerId: Int,
        @RequestBody savedAddress: SavedAddressCreateRequest
    ): ResponseEntity<ControllerResponse> {
        val (name, mobile, address1, address2, address3, city, state, country, pincode) = savedAddress
        val response = savedAddressService.createSavedAddress(
            customerId, name, mobile, address1, address2, address3, city, state, country, pincode
        )
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping
    fun getSavedAddresses(
        @PathVariable customerId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = savedAddressService.getSavedAddressesByCustomerId(customerId, page, pageSize)
        return responseEntity(response.status, mapOf("savedAddresses" to response.data?.map { it.toMap() }), response.message)
    }

    @GetMapping("/{savedAddressId}")
    fun getSavedAddress(
        @PathVariable customerId: Int,
        @PathVariable savedAddressId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = savedAddressService.getSavedAddressByCustomerIdAndSavedAddressId(customerId, savedAddressId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @PutMapping("/{savedAddressId}")
    fun updateSavedAddress(
        @PathVariable customerId: Int,
        @PathVariable savedAddressId: Int,
        @RequestBody savedAddress: SavedAddressUpdateRequest
    ): ResponseEntity<ControllerResponse> {
        val (name, mobile, address1, address2, address3, city, state, country, pincode) = savedAddress
        val response = savedAddressService.updateSavedAddress(
            customerId, savedAddressId, name, mobile, address1, address2, address3, city, state, country, pincode
        )
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @DeleteMapping("/{savedAddressId}")
    fun deleteSavedAddress(
        @PathVariable customerId: Int,
        @PathVariable savedAddressId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = savedAddressService.deleteSavedAddress(customerId, savedAddressId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

}