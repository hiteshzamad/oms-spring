package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
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
        @PathVariable customerId: Int, @RequestBody savedAddress: SavedAddressCreateRequest
    ): ResponseEntity<ControllerResponse> {
        val (name, mobile, address1, address2, address3, city, state, country, pincode) = savedAddress
        val response = savedAddressService.createSavedAddress(
            customerId, name, mobile, address1, address2, address3, city, state, country, pincode
        )
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "addressId" to data!!.addressId,
                    "customerId" to data.customerId,
                    "name" to data.name,
                    "mobile" to data.mobile,
                    "address1" to data.address1,
                    "address2" to data.address2,
                    "address3" to data.address3,
                    "city" to data.city,
                    "state" to data.state,
                    "country" to data.country,
                    "pincode" to data.pincode
                )
            ) else responseEntity(status)
        }
    }

    @GetMapping
    fun getSavedAddresses(
        @PathVariable customerId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = savedAddressService.getSavedAddressesByCustomerId(customerId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(status, mapOf("savedAddresses" to data!!.map {
                mapOf(
                    "addressId" to it.addressId,
                    "customerId" to it.customerId,
                    "name" to it.name,
                    "mobile" to it.mobile,
                    "address1" to it.address1,
                    "address2" to it.address2,
                    "address3" to it.address3,
                    "city" to it.city,
                    "state" to it.state,
                    "country" to it.country,
                    "pincode" to it.pincode
                )
            })) else responseEntity(status)
        }
    }

    @GetMapping("/{savedAddressId}")
    fun getSavedAddress(
        @PathVariable customerId: Int, @PathVariable savedAddressId: Int
    ): ResponseEntity<ControllerResponse> {
        val response = savedAddressService.getSavedAddressByCustomerIdAndSavedAddressId(customerId, savedAddressId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "addressId" to data!!.addressId,
                    "customerId" to data.customerId,
                    "name" to data.name,
                    "mobile" to data.mobile,
                    "address1" to data.address1,
                    "address2" to data.address2,
                    "address3" to data.address3,
                    "city" to data.city,
                    "state" to data.state,
                    "country" to data.country,
                    "pincode" to data.pincode
                )
            ) else responseEntity(status)
        }
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
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "addressId" to data!!.addressId,
                    "customerId" to data.customerId,
                    "name" to data.name,
                    "mobile" to data.mobile,
                    "address1" to data.address1,
                    "address2" to data.address2,
                    "address3" to data.address3,
                    "city" to data.city,
                    "state" to data.state,
                    "country" to data.country,
                    "pincode" to data.pincode
                )
            ) else responseEntity(status)
        }
    }

    @DeleteMapping("/{savedAddressId}")
    fun deleteSavedAddress(
        @PathVariable customerId: Int,
        @PathVariable savedAddressId: Int,
    ): ResponseEntity<ControllerResponse> {
        val response = savedAddressService.deleteSavedAddress(customerId, savedAddressId)
        with(response) {
            return responseEntity(status)
        }
    }
}