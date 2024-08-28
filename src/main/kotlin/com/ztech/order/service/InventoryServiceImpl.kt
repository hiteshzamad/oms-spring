package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.exception.RequestInvalidException
import com.ztech.order.exception.ResourceNotFoundException
import com.ztech.order.model.toDomain
import com.ztech.order.repository.jpa.InventoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.order.model.domain.Inventory as InventoryDomain
import com.ztech.order.model.entity.Inventory as InventoryEntity
import com.ztech.order.model.entity.Product as ProductEntity
import com.ztech.order.model.entity.Seller as SellerEntity

@Service
class InventoryServiceImpl(
    private val inventoryRepository: InventoryRepository,
    private val cartService: CartServiceImpl,
    private val transactionHandler: TransactionHandler
) {
    fun createInventory(sellerId: Int, productId: Int, quantity: Int, price: Double) =
        inventoryRepository.save(InventoryEntity().also { inventory ->
            inventory.seller = SellerEntity(sellerId)
            inventory.product = ProductEntity(productId)
            inventory.quantity = quantity
            inventory.price = price.toBigDecimal()
        }).toDomain(_product = false, _seller = false)

    fun getInventoriesByProductName(name: String, page: Int, pageSize: Int) =
        inventoryRepository.findByProductNameContainingIgnoreCase(name, PageRequest.of(page, pageSize))
            .map { it.toDomain() }

    fun getInventoriesBySellerId(sellerId: Int, page: Int, pageSize: Int) =
        inventoryRepository.findBySellerId(sellerId, PageRequest.of(page, pageSize))
            .map { it.toDomain(_seller = false) }

    fun getInventoryByInventoryId(inventoryId: Int) =
        inventoryRepository.findById(inventoryId).getOrElse {
            throw ResourceNotFoundException("Inventory not found")
        }.toDomain()

    fun getInventoryBySellerIdAndProductId(sellerId: Int, productId: Int) =
        inventoryRepository.findBySellerIdAndProductId(sellerId, productId).getOrElse {
            throw ResourceNotFoundException("Inventory not found")
        }.toDomain(_seller = false)


    fun getInventoryBySellerIdAndInventoryId(sellerId: Int, inventoryId: Int) =
        inventoryRepository.findByIdAndSellerId(inventoryId, sellerId).getOrElse {
            throw ResourceNotFoundException("Inventory not found")
        }.toDomain(_seller = false)

    fun updateInventoryByInventoryIdAndSellerId(
        sellerId: Int, inventoryId: Int, quantityChange: Int, price: Double
    ) = this.transactionHandler.execute {
        val quantity = inventoryRepository.findQuantityByIdAndSellerId(inventoryId, sellerId).getOrElse {
            throw ResourceNotFoundException("Inventory not found")
        }
        val newQuantity = quantity + quantityChange
        when {
            newQuantity >= 0 ->
                inventoryRepository.updateById(inventoryId, quantityChange, price)
            else -> throw RequestInvalidException("Quantity cannot be negative")
        }
    }

    fun updateQuantityByInventory(
        inventory: InventoryDomain, quantityChange: Int
    ) = this.transactionHandler.execute {
        val newQuantity = inventory.quantity + quantityChange
        when {
            newQuantity >= 0 ->
                inventoryRepository.updateById(inventory.id, quantityChange)
            else -> throw RequestInvalidException("Inventory not enough")
        }
    }

    fun deleteInventory(sellerId: Int, inventoryId: Int) = this.transactionHandler.execute {
        cartService.deleteCartByInventoryId(inventoryId)
        inventoryRepository.deleteByIdAndSellerId(inventoryId, sellerId)
    }

}