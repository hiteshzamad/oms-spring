package com.ztech.order.repository

import com.ztech.order.model.entity.Cart
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Int> {
    @EntityGraph("Cart.inventory.product_seller")
    fun findByCustomerCustomerId(customerId: Int): List<Cart>

    @EntityGraph("Cart.inventory.product_seller")
    fun findByCustomerCustomerIdAndCartId(customerId: Int, cartId: Int): Cart

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = c.quantity + :quantityChange WHERE c.cartId = :cartId")
    fun updateCartQuantityByCartId(
        @Param("cartId") cartId: Int,
        @Param("quantityChange") quantityChange: Int
    )

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.customer.customerId = :customerId AND c.cartId = :cartId")
    fun deleteByCustomerIdAndCartId(@Param("customerId") customerId: Int, @Param("cartId") cartId: Int)

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.customer.customerId = :customerId")
    fun deleteByCustomerId(@Param("customerId") customerId: Int)
}