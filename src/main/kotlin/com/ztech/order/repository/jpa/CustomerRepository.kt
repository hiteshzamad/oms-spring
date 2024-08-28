package com.ztech.order.repository.jpa

import com.ztech.order.model.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository : JpaRepository<Customer, Int> {
    fun findByAccountId(accountId: Int): Optional<Customer>
}