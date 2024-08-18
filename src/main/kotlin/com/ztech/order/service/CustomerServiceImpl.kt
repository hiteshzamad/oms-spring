package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.repository.CustomerRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.Customer as CustomerDomain
import com.ztech.order.model.entity.Account as AccountEntity
import com.ztech.order.model.entity.Customer as CustomerEntity

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
) : AbstractService() {
    fun createCustomer(accountId: Int, name: String) = tryCatchDaoCall {
        val responseGetCustomer = getCustomerByAccountId(accountId)
        when (responseGetCustomer.status) {
            Status.SUCCESS -> ServiceResponse(Status.CONFLICT)
            Status.NOT_FOUND -> {
                val savedEntity = customerRepository.save(CustomerEntity().also {
                    it.account = AccountEntity(accountId)
                    it.name = name
                })
                ServiceResponse(Status.SUCCESS, savedEntity.toDomain())
            }
            else -> responseGetCustomer
        }
    }

    fun getCustomers() = tryCatchDaoCall {
        val customers = customerRepository.findAll().map { it.toDomain() }
        ServiceResponse(Status.SUCCESS, customers)
    }

    fun getCustomerByAccountId(accountId: Int) = tryCatchDaoCall {
        val customer = customerRepository.findByAccountAccountId((accountId))
        ServiceResponse(Status.SUCCESS, customer.toDomain())
    }

    fun getCustomerByCustomerId(customerId: Int) = tryCatchDaoCall {
        val customer = customerRepository.findByCustomerId(customerId).toDomain()
        ServiceResponse(Status.SUCCESS, customer)
    }

}