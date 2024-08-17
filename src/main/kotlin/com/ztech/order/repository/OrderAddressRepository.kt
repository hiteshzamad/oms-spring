package com.ztech.order.repository

import com.ztech.order.model.entity.OrderAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderAddressRepository : JpaRepository<OrderAddress, Int> {
    fun findByOrderAddressId(addressId: Int): OrderAddress
    fun findByOrderOrderId(orderId: Int): OrderAddress
    fun findByOrderOrderIdAndOrderAddressId(orderId: Int, addressId: Int): OrderAddress
    fun deleteByOrderOrderIdAndOrderAddressId(orderId: Int, addressId: Int)
}
