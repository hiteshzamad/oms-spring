package com.ztech.order.repository

import com.ztech.order.model.entity.SavedAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SavedAddressRepository : JpaRepository<SavedAddress, Int> {
    fun findByCustomerCustomerId(customerId: Int): List<SavedAddress>
    fun findByCustomerCustomerIdAndSavedAddressId(customerId: Int, addressId: Int): SavedAddress
    fun deleteByCustomerCustomerIdAndSavedAddressId(customerId: Int, addressId: Int)
}