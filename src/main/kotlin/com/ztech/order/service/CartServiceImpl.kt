package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.core.TransactionHandler
import com.ztech.order.repository.CartRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.order.model.domain.Cart as CartDomain
import com.ztech.order.model.entity.Cart as CartEntity
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.Inventory as InventoryEntity

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val transactionHandler: TransactionHandler
) : AbstractService() {
    fun createCart(customerId: Int, inventoryId: Int, quantity: Int) = tryCatch {
        ServiceResponse(
            Status.SUCCESS, cartRepository.save(CartEntity().also { cart ->
                cart.customer = CustomerEntity(customerId)
                cart.inventory = InventoryEntity(inventoryId)
                cart.quantity = quantity
            }).toDomain(false)
        )
    }

    fun getCartsByCustomerId(customerId: Int) = tryCatch {
        val entities = cartRepository.findByCustomerCustomerId(customerId)
        ServiceResponse(Status.SUCCESS, entities.map { it.toDomain() })
    }

    fun getCartByCustomerIdAndCartId(customerId: Int, cartId: Int) = tryCatch {
        val entity = cartRepository.findByCustomerCustomerIdAndCartId(customerId, cartId)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

    fun updateOrDeleteCart(customerId: Int, cartId: Int, quantityChange: Int) = tryCatch {
        this.transactionHandler.execute {
            val cart = cartRepository.findById(cartId).getOrElse {
                return@execute ServiceResponse(Status.NOT_FOUND, null, "Cart not found")
            }
            val newEntity = CartEntity(cart.cartId)
            newEntity.customer = CustomerEntity(cart.cartId)
            newEntity.inventory = InventoryEntity(cart.inventory.inventoryId)
            val newQuantity = cart.quantity + quantityChange
            newEntity.quantity = newQuantity
            if (newQuantity > 0) {
                val updatedEntity = cartRepository.save(newEntity)
                ServiceResponse(Status.SUCCESS, updatedEntity.toDomain(false))
            } else if (newQuantity == 0) {
                cartRepository.deleteByCustomerCustomerIdAndCartId(customerId, cartId)
                ServiceResponse(Status.SUCCESS, null)
            } else {
                ServiceResponse(Status.INVALID_INPUT, null, "Quantity cannot be negative")
            }
        }
    }

    fun deleteCartsByCustomerId(customerId: Int) = tryCatch {
        this.transactionHandler.execute {
            cartRepository.deleteByCustomerCustomerId(customerId)
            ServiceResponse<CartDomain>(Status.SUCCESS)
        }
    }

    fun deleteCart(customerId: Int, cartId: Int) = tryCatch {
        this.transactionHandler.execute {
            cartRepository.deleteByCustomerCustomerIdAndCartId(customerId, cartId)
            ServiceResponse<CartDomain>(Status.SUCCESS)
        }
    }

}