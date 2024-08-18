package com.ztech.order.repository

import com.ztech.order.model.entity.Cart
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Int> {
    @EntityGraph("Cart.inventory.product")
    fun findByCustomerCustomerId(customerId: Int, pageRequest: PageRequest): List<Cart>
    @EntityGraph("Cart.inventory.product")
    fun findByCustomerCustomerIdAndCartId(customerId: Int, cartId: Int): Cart
    fun deleteByCustomerCustomerIdAndCartId(customerId: Int, cartId: Int)
}