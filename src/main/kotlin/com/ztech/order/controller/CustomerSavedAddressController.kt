package com.ztech.order.controller

import com.ztech.order.model.dto.SavedAddressCreateRequest
import com.ztech.order.model.dto.SavedAddressUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
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
    ): ResponseEntity<Response> {
        val (name, mobile, address1, address2, address3, city, state, country, pincode) = savedAddress
        val response = savedAddressService.createSavedAddress(
            customerId, name, mobile, address1, address2, address3, city, state, country, pincode
        )
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getSavedAddresses(
        @PathVariable customerId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Response> {
        val response = savedAddressService.getSavedAddressesByCustomerId(customerId, page, pageSize)
        return responseSuccess(mapOf("savedAddresses" to response.map { it.toMap() }))
    }

    @GetMapping("/{savedAddressId}")
    fun getSavedAddress(
        @PathVariable customerId: Int,
        @PathVariable savedAddressId: Int
    ): ResponseEntity<Response> {
        val response = savedAddressService.getSavedAddressByCustomerIdAndSavedAddressId(customerId, savedAddressId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{savedAddressId}")
    fun updateSavedAddress(
        @PathVariable customerId: Int,
        @PathVariable savedAddressId: Int,
        @RequestBody savedAddress: SavedAddressUpdateRequest
    ): ResponseEntity<Response> {
        val (name, mobile, address1, address2, address3, city, state, country, pincode) = savedAddress
        savedAddressService.updateSavedAddress(
            customerId, savedAddressId, name, mobile, address1, address2, address3, city, state, country, pincode
        )
        return responseSuccess()
    }

    @DeleteMapping("/{savedAddressId}")
    fun deleteSavedAddress(
        @PathVariable customerId: Int,
        @PathVariable savedAddressId: Int,
    ): ResponseEntity<Response> {
        savedAddressService.deleteSavedAddress(customerId, savedAddressId)
        return responseSuccess()
    }

}