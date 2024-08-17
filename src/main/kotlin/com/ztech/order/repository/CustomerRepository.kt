package com.ztech.order.repository

import com.ztech.order.model.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Int> {
    fun findByAccountAccountId(accountId: Int): Customer
    fun findByCustomerId(customerId: Int): Customer
}