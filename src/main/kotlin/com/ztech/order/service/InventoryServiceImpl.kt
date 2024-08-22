package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.core.TransactionHandler
import com.ztech.order.repository.InventoryRepository
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
    private val transactionHandler: TransactionHandler
) : AbstractService() {
    fun createInventory(sellerId: Int, productId: Int, quantity: Int, price: Double) = tryCatch {
        val entity = inventoryRepository.save(InventoryEntity().also { inventory ->
            inventory.seller = SellerEntity(sellerId)
            inventory.product = ProductEntity(productId)
            inventory.quantity = quantity
            inventory.price = price.toBigDecimal()
        })
        ServiceResponse(Status.SUCCESS, entity.toDomain(false, false))
    }

    fun getInventoriesByProductName(name: String, page: Int, pageSize: Int) = tryCatch {
        val entities = inventoryRepository.findByProductNameContainingIgnoreCase(name, PageRequest.of(page, pageSize))
        ServiceResponse(Status.SUCCESS, entities.map { it.toDomain() })
    }

    fun getInventoriesBySellerId(sellerId: Int, page: Int, pageSize: Int) = tryCatch {
        val entities = inventoryRepository.findBySellerSellerId(sellerId, PageRequest.of(page, pageSize))
        ServiceResponse(Status.SUCCESS, entities.map { it.toDomain(seller = false) })
    }

    fun getInventoryByInventoryId(inventoryId: Int) = tryCatch {
        val entity = inventoryRepository.findByInventoryId(inventoryId)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

    fun getInventoryBySellerIdAndInventoryId(sellerId: Int, inventoryId: Int) = tryCatch {
        val entity = inventoryRepository.findBySellerSellerIdAndInventoryId(sellerId, inventoryId)
        ServiceResponse(Status.SUCCESS, entity.toDomain(seller = false))
    }

        fun updateInventory(sellerId: Int, inventoryId: Int, quantityChange: Int, price: Double) = tryCatch {
            this.transactionHandler.execute {
            val inventory = inventoryRepository.findById(inventoryId).getOrElse {
                return@execute ServiceResponse(Status.NOT_FOUND, null, "Inventory not found")
            }
            val newEntity = InventoryEntity(inventory.inventoryId)
            newEntity.seller = SellerEntity(inventory.seller.sellerId)
            newEntity.product = ProductEntity(inventory.product.productId)
            newEntity.price = price.toBigDecimal()
            val newQuantity = inventory.quantity + quantityChange
            if (newQuantity >= 0) {
                newEntity.quantity = newQuantity
                val updatedEntity = inventoryRepository.save(newEntity)
                ServiceResponse(Status.SUCCESS, updatedEntity.toDomain(false, false))
            } else {
                ServiceResponse(Status.INVALID_INPUT, null, "Quantity cannot be negative")
            }
        }
    }

    fun deleteInventory(sellerId: Int, inventoryId: Int) = tryCatch {
        this.transactionHandler.execute {
            inventoryRepository.deleteBySellerSellerIdAndInventoryId(sellerId, inventoryId)
            ServiceResponse<InventoryDomain>(Status.SUCCESS)
        }
    }

}