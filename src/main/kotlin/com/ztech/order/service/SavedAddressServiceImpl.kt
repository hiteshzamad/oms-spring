package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.model.toDomain
import com.ztech.order.repository.SavedAddressRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.SavedAddress as SavedAddressEntity

@Service
class SavedAddressServiceImpl(
    private val savedAddressRepository: SavedAddressRepository,
    private val transactionHandler: TransactionHandler
) {
    fun createSavedAddress(
        customerId: Int, name: String, mobile: String, address1: String, address2: String?, address3: String?,
        city: String, state: String, country: String, pincode: String,
    ) = savedAddressRepository.save(SavedAddressEntity().also { newEntity ->
        newEntity.customer = CustomerEntity(customerId)
        newEntity.name = name
        newEntity.mobile = mobile
        newEntity.address1 = address1
        newEntity.address2 = address2
        newEntity.address3 = address3
        newEntity.city = city
        newEntity.state = state
        newEntity.country = country
        newEntity.pincode = pincode
    }).toDomain()

    fun getSavedAddressesByCustomerId(customerId: Int, page: Int, pageSize: Int) =
        savedAddressRepository.findByCustomerCustomerId(customerId, PageRequest.of(page, pageSize))
            .map { it.toDomain() }

    fun getSavedAddressByCustomerIdAndSavedAddressId(customerId: Int, savedAddressId: Int) =
        savedAddressRepository.findByCustomerCustomerIdAndSavedAddressId(customerId, savedAddressId).toDomain()

    fun updateSavedAddress(
        customerId: Int, savedAddressId: Int, name: String, mobile: String,
        address1: String, address2: String?, address3: String?,
        city: String, state: String, country: String, pincode: String,
    ) = this.transactionHandler.execute {
        savedAddressRepository.updateSavedAddressBySavedAddressId(
            customerId, savedAddressId, name, mobile, address1, address2, address3, city, state, country, pincode
        )
    }

    fun deleteSavedAddress(customerId: Int, savedAddressId: Int) = this.transactionHandler.execute {
        savedAddressRepository.deleteByCustomerCustomerIdAndSavedAddressId(customerId, savedAddressId)
    }
}