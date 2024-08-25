package com.ztech.order.repository

import com.ztech.order.model.entity.Seller
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SellerRepository : JpaRepository<Seller, Int> {
    fun findByAccountId(accountId: Int): Optional<Seller>
}