package com.ztech.order.repository

import com.ztech.order.model.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Int> {
    fun findByUsername(username: String): Account
    fun findByAccountId(accountId: Int): Account
}