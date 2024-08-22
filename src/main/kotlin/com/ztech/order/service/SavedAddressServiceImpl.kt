package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.core.TransactionHandler
import com.ztech.order.repository.SavedAddressRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.order.model.domain.SavedAddress as SavedAddressDomain
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.SavedAddress as SavedAddressEntity

@Service
class SavedAddressServiceImpl(
    private val savedAddressRepository: SavedAddressRepository,
    private val transactionHandler: TransactionHandler
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
    ) = tryCatch {
        val entity = savedAddressRepository.save(SavedAddressEntity().also { newEntity ->
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
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

    fun getSavedAddressesByCustomerId(customerId: Int, page: Int, pageSize: Int) = tryCatch {
        val entities = savedAddressRepository.findByCustomerCustomerId(customerId, PageRequest.of(page, pageSize))
        ServiceResponse(Status.SUCCESS, entities.map { it.toDomain() })
    }

    fun getSavedAddressByCustomerIdAndSavedAddressId(customerId: Int, savedAddressId: Int) = tryCatch {
        val entity = savedAddressRepository.findByCustomerCustomerIdAndSavedAddressId(customerId, savedAddressId)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
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
    ) = tryCatch {
        this.transactionHandler.execute {
            val address = savedAddressRepository.findById(savedAddressId).getOrElse {
                return@execute ServiceResponse(Status.NOT_FOUND, null, "Cart not found")
            }
            val newEntity = SavedAddressEntity(address.savedAddressId)
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
            val updatedEntity = savedAddressRepository.save(newEntity)
            ServiceResponse(Status.SUCCESS, updatedEntity.toDomain())
        }
    }

    fun deleteSavedAddress(customerId: Int, savedAddressId: Int) = tryCatch {
        this.transactionHandler.execute {
            savedAddressRepository.deleteByCustomerCustomerIdAndSavedAddressId(customerId, savedAddressId)
            ServiceResponse<SavedAddressDomain>(Status.SUCCESS)
        }
    }

}