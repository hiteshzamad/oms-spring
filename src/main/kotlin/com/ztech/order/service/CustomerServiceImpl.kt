package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.repository.CustomerRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.entity.Account as AccountEntity
import com.ztech.order.model.entity.Customer as CustomerEntity

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
) : AbstractService() {
    fun createCustomer(accountId: Int, name: String) = tryCatch {
        val savedEntity = customerRepository.save(CustomerEntity().also {
            it.account = AccountEntity(accountId)
            it.name = name
        })
        ServiceResponse(Status.SUCCESS, savedEntity.toDomain())
    }

    fun getCustomerByAccountId(accountId: Int) = tryCatch {
        val entity = customerRepository.findByAccountAccountId((accountId))
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

    fun getCustomerByCustomerId(customerId: Int) = tryCatch {
        val entity = customerRepository.findByCustomerId(customerId)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

}