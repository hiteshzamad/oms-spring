package com.ztech.order.repository

import com.ztech.order.model.entity.Seller
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SellerRepository : JpaRepository<Seller, Int> {
    fun findByAccountAccountId(accountId: Int): Seller
    fun findBySellerId(sellerId: Int): Seller
}