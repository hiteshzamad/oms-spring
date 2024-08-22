package com.ztech.order.repository

import com.ztech.order.model.entity.Inventory
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
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
    fun findBySellerSellerId(sellerId: Int, pageRequest: PageRequest): List<Inventory>

    fun deleteBySellerSellerIdAndInventoryId(sellerId: Int, inventoryId: Int)
}