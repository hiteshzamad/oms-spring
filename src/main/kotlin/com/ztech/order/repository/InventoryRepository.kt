package com.ztech.order.repository

import com.ztech.order.model.entity.Inventory
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface InventoryRepository : JpaRepository<Inventory, Int> {
    @EntityGraph("Inventory.product_seller")
    fun findByInventoryId(inventoryId: Int): Inventory

    @EntityGraph("Inventory.product_seller")
    fun findByProductNameContainingIgnoreCase(name: String, pageRequest: PageRequest): List<Inventory>

    @EntityGraph("Inventory.product")
    fun findBySellerSellerIdAndInventoryId(sellerId: Int, inventoryId: Int): Inventory

    @EntityGraph("Inventory.product")
    fun findBySellerSellerIdAndProductProductId(sellerId: Int, productId: Int): Inventory

    @EntityGraph("Inventory.product")
    fun findBySellerSellerId(sellerId: Int, pageRequest: PageRequest): List<Inventory>

    @Modifying
    @Query(
        "UPDATE Inventory i " +
                "SET i.quantity = i.quantity + :quantityChange, " +
                "i.price = :price " +
                "WHERE i.inventoryId = :inventoryId"
    )
    fun updateQuantityAndPriceByInventoryId(
        @Param("inventoryId") inventoryId: Int,
        @Param("quantityChange") quantityChange: Int,
        @Param("price") price: Double
    )

    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity + :quantityChange WHERE i.inventoryId = :inventoryId")
    fun updateQuantityByInventoryId(
        @Param("inventoryId") inventoryId: Int,
        @Param("quantityChange") quantityChange: Int
    )

    @Modifying
    @Query("DELETE FROM Inventory i WHERE i.seller.sellerId = :sellerId AND i.inventoryId = :inventoryId")
    fun deleteBySellerSellerIdAndInventoryId(@Param("sellerId") sellerId: Int, @Param("inventoryId") inventoryId: Int)
}