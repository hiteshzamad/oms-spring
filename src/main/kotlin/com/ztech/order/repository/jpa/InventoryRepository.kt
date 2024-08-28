package com.ztech.order.repository.jpa

import com.ztech.order.model.entity.Inventory
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InventoryRepository : JpaRepository<Inventory, Int> {
    @EntityGraph("InventoryWithProductAndSeller")
    override fun findById(id: Int): Optional<Inventory>

    @EntityGraph("InventoryWithProduct")
    fun findBySellerId(sellerId: Int, pageRequest: PageRequest): List<Inventory>

    @EntityGraph("InventoryWithProduct")
    fun findByIdAndSellerId(id: Int, sellerId: Int): Optional<Inventory>

    @Query("SELECT i.quantity FROM Inventory i WHERE i.id = :id AND i.seller.id = :sellerId")
    fun findQuantityByIdAndSellerId(@Param("id") id: Int, @Param("sellerId") sellerId: Int): Optional<Int>

    @EntityGraph("InventoryWithProduct")
    fun findBySellerIdAndProductId(
        @Param("sellerId") sellerId: Int,
        @Param("productId") productId: Int
    ): Optional<Inventory>

    @EntityGraph("InventoryWithProductAndSeller")
    fun findByProductNameContainingIgnoreCase(name: String, pageRequest: PageRequest): List<Inventory>

    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity + :quantityChange WHERE i.id = :inventoryId")
    fun updateById(
        @Param("inventoryId") id: Int,
        @Param("quantityChange") quantityChange: Int
    )

    @Modifying
    @Query(
        "UPDATE Inventory i " +
                "SET i.quantity = i.quantity + :quantityChange, i.price = :price " +
                "WHERE i.id = :inventoryId"
    )
    fun updateById(
        @Param("inventoryId") id: Int,
        @Param("quantityChange") quantityChange: Int,
        @Param("price") price: Double
    )

    @Modifying
    @Query("DELETE FROM Inventory i WHERE i.id = :inventoryId AND i.seller.id = :sellerId")
    fun deleteByIdAndSellerId(@Param("inventoryId") id: Int, @Param("sellerId") sellerId: Int)
}