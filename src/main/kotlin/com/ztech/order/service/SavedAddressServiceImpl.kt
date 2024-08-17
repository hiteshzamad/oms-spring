package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.repository.SavedAddressRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.SavedAddress as SavedAddressDomain
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.SavedAddress as SavedAddressEntity

@Service
class SavedAddressServiceImpl(
    private val savedAddressRepository: SavedAddressRepository
) : AbstractService() {
    fun createSavedAddress(
        customerId: Int,
        name: String,
        mobile: String,
        address1: String,
        address2: String?,
        address3: String?,
        city: String,
        state: String,
        country: String,
        pincode: String,
    ) = tryCatchDaoCall {
        val address = savedAddressRepository.save(SavedAddressEntity().also { newEntity ->
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
        })
        ServiceResponse(Status.SUCCESS, address.toDomain())
    }

    fun getSavedAddressesByCustomerId(customerId: Int) = tryCatchDaoCall {
        val addresses = savedAddressRepository.findByCustomerCustomerId(customerId).map { it.toDomain() }
        ServiceResponse(Status.SUCCESS, addresses)
    }

    fun getSavedAddressByCustomerIdAndSavedAddressId(customerId: Int, savedAddressId: Int) = tryCatchDaoCall {
        val address = savedAddressRepository.findByCustomerCustomerIdAndSavedAddressId(customerId, savedAddressId)
        ServiceResponse(Status.SUCCESS, address.toDomain())
    }

    fun updateSavedAddress(
        customerId: Int,
        savedAddressId: Int,
        name: String,
        mobile: String,
        address1: String,
        address2: String?,
        address3: String?,
        city: String,
        state: String,
        country: String,
        pincode: String,
    ) = tryCatchDaoCall {
        val responseGetAddress = getSavedAddressByCustomerIdAndSavedAddressId(customerId, savedAddressId)
        when (responseGetAddress.status) {
            Status.SUCCESS ->
                responseGetAddress.data!!.let { address ->
                    val newEntity = SavedAddressEntity(address.addressId)
                    newEntity.customer = CustomerEntity(address.customerId)
                    newEntity.name = name
                    newEntity.mobile = mobile
                    newEntity.address1 = address1
                    newEntity.address2 = address2
                    newEntity.address3 = address3
                    newEntity.city = city
                    newEntity.state = state
                    newEntity.country = country
                    newEntity.pincode = pincode
                    val updatedEntity = savedAddressRepository.save(newEntity)
                    ServiceResponse(Status.SUCCESS, updatedEntity.toDomain())
                }
            else -> responseGetAddress
        }
    }

    fun deleteSavedAddress(customerId: Int, savedAddressId: Int) = tryCatchDaoCall {
        savedAddressRepository.deleteByCustomerCustomerIdAndSavedAddressId(customerId, savedAddressId)
        ServiceResponse<SavedAddressDomain>(Status.SUCCESS)
    }

    private fun SavedAddressEntity.toDomain() = SavedAddressDomain(
        addressId = savedAddressId!!,
        customerId = customer.customerId!!,
        name = name,
        mobile = mobile,
        address1 = address1,
        address2 = address2,
        address3 = address3,
        city = city,
        state = state,
        country = country,
        pincode = pincode,
    )
}