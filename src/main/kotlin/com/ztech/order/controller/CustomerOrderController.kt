package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.Status
import com.ztech.order.core.responseEntity
import com.ztech.order.model.dto.OrderCreateRequest
import com.ztech.order.service.CartServiceImpl
import com.ztech.order.service.OrderServiceImpl
import com.ztech.order.service.SavedAddressServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers/{customerId}/orders")
class CustomerOrderController(
    private val orderService: OrderServiceImpl,
    private val savedAddressService: SavedAddressServiceImpl,
    private val cartService: CartServiceImpl
) {

    @PostMapping
    fun createOrder(
        @PathVariable customerId: Int,
        @RequestBody order: OrderCreateRequest
    ): ResponseEntity<ControllerResponse> {
        val (savedAddressId, paymentMethod) = order
        val responseSAdd = savedAddressService.getSavedAddressByCustomerIdAndSavedAddressId(customerId, savedAddressId)
        if (responseSAdd.status != Status.SUCCESS)
            return responseEntity(responseSAdd.status, null, responseSAdd.message)
        val responseCart = cartService.getCartsByCustomerId(customerId)
        if (responseCart.status != Status.SUCCESS) return responseEntity(responseCart.status)
        val responseOrder =
            orderService.createOrder(customerId, paymentMethod, responseSAdd.data!!, responseCart.data!!)
        return responseEntity(responseOrder.status, responseOrder.data?.toMap(), responseOrder.message)
    }

}