package com.ztech.order.repository.jpa

import com.ztech.order.model.entity.Cart
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartRepository : JpaRepository<Cart, Int> {
    @EntityGraph("CartWithInventoryWithProductAndSeller")
    fun findByCustomerId(customerId: Int): List<Cart>

    @EntityGraph("CartWithInventory")
    fun findCartWithInventoryByCustomerId(customerId: Int): List<Cart>

    @EntityGraph("CartWithInventoryWithProductAndSeller")
    fun findByIdAndCustomerId(id: Int, customerId: Int): Optional<Cart>

    @Query("SELECT c.quantity FROM Cart c WHERE c.id = :id AND c.customer.id = :customerId")
    fun findQuantityByIdAndCustomerId(@Param("id") id: Int, @Param("customerId") customerId: Int): Optional<Int>

    @Modifying
    @Query(
        "UPDATE Cart c SET c.quantity = c.quantity + :quantityChange WHERE c.id = :cartId"
    )
    fun updateById(
        @Param("cartId") id: Int,
        @Param("quantityChange") quantityChange: Int
    )

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.customer.id = :customerId")
    fun deleteByCustomerId(@Param("customerId") customerId: Int)

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id = :cartId AND c.customer.id = :customerId")
    fun deleteByIdAndCustomerId(@Param("cartId") d: Int, @Param("customerId") customerId: Int)

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.inventory.id = :inventoryId")
    fun deleteByInventoryId(@Param("inventoryId") inventoryId: Int)

}