package com.ztech.order.service

import com.ztech.order.model.toDomain
import com.ztech.order.repository.CustomerRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.entity.Account as AccountEntity
import com.ztech.order.model.entity.Customer as CustomerEntity

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
) {
    fun createCustomer(accountId: Int, name: String) =
        customerRepository.save(CustomerEntity().also {
            it.account = AccountEntity(accountId)
            it.name = name
        }).toDomain()

    fun getCustomerByAccountId(accountId: Int) =
        customerRepository.findByAccountAccountId((accountId)).toDomain()

    fun getCustomerByCustomerId(customerId: Int) =
        customerRepository.findByCustomerId(customerId).toDomain()

}