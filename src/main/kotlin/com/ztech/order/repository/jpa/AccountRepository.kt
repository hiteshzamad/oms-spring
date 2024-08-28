package com.ztech.order.repository.jpa

import com.ztech.order.model.entity.Account
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Int> {

    @EntityGraph("AccountWithCustomerAndSeller")
    override fun findById(id: Int): Optional<Account>

    @EntityGraph("AccountWithCustomerAndSeller")
    fun findByUsername(username: String): Optional<Account>

    @Modifying
    @Query(
        "UPDATE Account a " +
                "SET a.password = CASE WHEN :password IS NOT NULL THEN :password ELSE a.password END, " +
                "a.email = CASE WHEN :email IS NOT NULL THEN :email ELSE a.email END, " +
                "a.mobile = CASE WHEN :mobile IS NOT NULL THEN :mobile ELSE a.mobile END " +
                "WHERE a.id = :accountId"
    )
    fun updateById(
        @Param("accountId") id: Int,
        @Param("password") password: String?,
        @Param("email") email: String?,
        @Param("mobile") mobile: String?
    )
}