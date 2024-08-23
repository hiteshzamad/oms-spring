package com.ztech.order.repository

import com.ztech.order.model.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Int> {
    fun findByUsername(username: String): Account
    fun findByAccountId(accountId: Int): Account

    @Modifying
    @Query(
        "UPDATE Account a " +
                "SET a.password = CASE WHEN :password IS NOT NULL THEN :password ELSE a.password END, " +
                "a.email = CASE WHEN :email IS NOT NULL THEN :email ELSE a.email END, " +
                "a.mobile = CASE WHEN :mobile IS NOT NULL THEN :mobile ELSE a.mobile END " +
                "WHERE a.accountId = :accountId"
    )
    fun updateAccountByAccountId(
        @Param("accountId") accountId: Int,
        @Param("password") password: String?,
        @Param("email") email: String?,
        @Param("mobile") mobile: String?
    )
}