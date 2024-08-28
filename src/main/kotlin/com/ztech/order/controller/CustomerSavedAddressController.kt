package com.ztech.order.controller

import com.ztech.order.model.dto.SavedAddressCreateRequest
import com.ztech.order.model.dto.SavedAddressUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.model.validator.ValidId
import com.ztech.order.service.SavedAddressServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers/{customerId}/savedAddresses")
@PreAuthorize("#customerId == authentication.principal.cid")
class CustomerSavedAddressController(
    private val savedAddressService: SavedAddressServiceImpl
) {

    @PostMapping
    fun createSavedAddress(
        @PathVariable @ValidId customerId: Int,
        @RequestBody @Valid savedAddress: SavedAddressCreateRequest
    ): ResponseEntity<Response> {
        val (name, mobile, address1, address2, address3, city, state, country, pincode) = savedAddress
        val response = savedAddressService.createSavedAddress(
            customerId, name, mobile, address1, address2, address3, city, state, country, pincode
        )
        return responseSuccess(response.toMap())
    }

    @GetMapping
    fun getSavedAddresses(
        @PathVariable @ValidId customerId: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Response> {
        val response = savedAddressService.getSavedAddressesByCustomerId(customerId, page, pageSize)
        return responseSuccess(mapOf("savedAddresses" to response.map { it.toMap() }))
    }

    @GetMapping("/{savedAddressId}")
    fun getSavedAddress(
        @PathVariable @ValidId customerId: Int,
        @PathVariable @ValidId savedAddressId: Int
    ): ResponseEntity<Response> {
        val response = savedAddressService.getSavedAddressByCustomerIdAndSavedAddressId(customerId, savedAddressId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{savedAddressId}")
    fun updateSavedAddress(
        @PathVariable @ValidId customerId: Int,
        @PathVariable @ValidId savedAddressId: Int,
        @RequestBody @Valid savedAddress: SavedAddressUpdateRequest
    ): ResponseEntity<Response> {
        val (name, mobile, address1, address2, address3, city, state, country, pincode) = savedAddress
        savedAddressService.updateSavedAddress(
            customerId, savedAddressId, name, mobile, address1, address2, address3, city, state, country, pincode
        )
        return responseSuccess()
    }

    @DeleteMapping("/{savedAddressId}")
    fun deleteSavedAddress(
        @PathVariable @ValidId customerId: Int,
        @PathVariable @ValidId savedAddressId: Int,
    ): ResponseEntity<Response> {
        savedAddressService.deleteSavedAddress(customerId, savedAddressId)
        return responseSuccess()
    }

}