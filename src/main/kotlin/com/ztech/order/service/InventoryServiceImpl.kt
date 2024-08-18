package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.repository.InventoryRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.Inventory as InventoryDomain
import com.ztech.order.model.entity.Inventory as InventoryEntity
import com.ztech.order.model.entity.Product as ProductEntity
import com.ztech.order.model.entity.Seller as SellerEntity

@Service
class InventoryServiceImpl(
    private val inventoryRepository: InventoryRepository
) : AbstractService() {
    fun createInventory(sellerId: Int, productId: Int, quantity: Int, price: Double) = tryCatchDaoCall {
        val inventory = inventoryRepository.save(InventoryEntity().also { inventory ->
            inventory.seller = SellerEntity(sellerId)
            inventory.product = ProductEntity(productId)
            inventory.quantity = quantity
            inventory.price = price.toBigDecimal()
        })
        ServiceResponse(Status.SUCCESS, inventory.toDomain())
    }

    fun getInventories() = tryCatchDaoCall {
        val inventories = inventoryRepository.findAll().map { it.toDomain() }
        ServiceResponse(Status.SUCCESS, inventories)
    }

    fun getInventoriesByProductId(productId: Int) = tryCatchDaoCall {
        val inventory = inventoryRepository.findByProductProductId(productId).map { it.toDomain() }
        ServiceResponse(Status.SUCCESS, inventory)
    }

    fun getInventoriesBySellerId(sellerId: Int) = tryCatchDaoCall {
        val inventories = inventoryRepository.findBySellerSellerId(sellerId).map { it.toDomain() }
        ServiceResponse(Status.SUCCESS, inventories)
    }

    fun getInventoryByInventoryId(inventoryId: Int) = tryCatchDaoCall {
        val inventory = inventoryRepository.findByInventoryId(inventoryId)
        ServiceResponse(Status.SUCCESS, inventory.toDomain())
    }

    fun getInventoryBySellerIdAndInventoryId(sellerId: Int, inventoryId: Int) = tryCatchDaoCall {
        val inventory = inventoryRepository.findBySellerSellerIdAndInventoryId(sellerId, inventoryId)
        ServiceResponse(Status.SUCCESS, inventory.toDomain())
    }

    fun updateInventory(sellerId: Int, inventoryId: Int, quantity: Int, price: Double) = tryCatchDaoCall {
        val responseGetInventory = getInventoryBySellerIdAndInventoryId(sellerId, inventoryId)
        when (responseGetInventory.status) {
            Status.SUCCESS -> responseGetInventory.data!!.let { inventory ->
                val newEntity = InventoryEntity(inventory.inventoryId)
                newEntity.seller = SellerEntity(inventory.sellerId)
                newEntity.product = ProductEntity(inventory.productId)
                newEntity.quantity = quantity
                newEntity.price = price.toBigDecimal()
                val updatedEntity = inventoryRepository.save(newEntity)
                ServiceResponse(Status.SUCCESS, updatedEntity.toDomain())
            }
            else -> responseGetInventory
        }
    }

    fun deleteInventory(sellerId: Int, inventoryId: Int) = tryCatchDaoCall {
        inventoryRepository.deleteBySellerSellerIdAndInventoryId(sellerId, inventoryId)
        ServiceResponse<InventoryDomain>(Status.SUCCESS)
    }

    private fun InventoryEntity.toDomain() = InventoryDomain(
        inventoryId = inventoryId!!,
        sellerId = seller.sellerId!!,
        productId = product.productId!!,
        quantity = quantity,
        price = price.toDouble()
    )
}