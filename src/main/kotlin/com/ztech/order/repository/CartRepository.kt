package com.ztech.order.repository

import com.ztech.order.model.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Int> {
    fun findByCustomerCustomerId(customerId: Int): List<Cart>
    fun findByCustomerCustomerIdAndCartId(customerId: Int, cartId: Int): Cart
    fun deleteByCustomerCustomerIdAndCartId(customerId: Int, cartId: Int)
}