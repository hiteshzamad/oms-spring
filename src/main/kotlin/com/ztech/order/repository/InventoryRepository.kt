package com.ztech.order.repository

import com.ztech.order.model.entity.Inventory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryRepository : JpaRepository<Inventory, Int> {
    fun findByInventoryId(inventoryId: Int): Inventory
    fun findBySellerSellerId(sellerId: Int): List<Inventory>
    fun findByProductProductId(productId: Int): List<Inventory>
    fun findBySellerSellerIdAndInventoryId(sellerId: Int, inventoryId: Int): Inventory
    fun deleteBySellerSellerIdAndInventoryId(sellerId: Int, inventoryId: Int)
}