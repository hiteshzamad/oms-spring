package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.exception.RequestInvalidException
import com.ztech.order.exception.ResourceNotFoundException
import com.ztech.order.model.toDomain
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
) {
    fun createCart(customerId: Int, inventoryId: Int, quantity: Int) = cartRepository.save(CartEntity().also { cart ->
        cart.customer = CustomerEntity(customerId)
        cart.inventory = InventoryEntity(inventoryId)
        cart.quantity = quantity
    }).toDomain(false)

    fun getCartsByCustomerId(customerId: Int) =
        cartRepository.findByCustomerCustomerId(customerId).map { it.toDomain() }

    fun getCartByCustomerIdAndCartId(customerId: Int, cartId: Int) =
        cartRepository.findByCustomerCustomerIdAndCartId(customerId, cartId).toDomain()

    fun updateCartByCartId(cartId: Int, quantityChange: Int) =
        this.transactionHandler.execute {
            val cart = cartRepository.findById(cartId).getOrElse {
                throw ResourceNotFoundException("Cart not found")
            }.toDomain(false)
            this.updateQuantityByCart(cart, quantityChange)
        }

    fun updateQuantityByCart(cart: CartDomain, quantityChange: Int) = this.transactionHandler.execute {
        val newQuantity = cart.quantity + quantityChange
        when {
            newQuantity > 0 -> cartRepository.updateCartQuantityByCartId(cart.cartId, quantityChange)
            else -> throw RequestInvalidException("Quantity cannot be less than 0")
        }
    }

    fun deleteCartsByCustomerId(customerId: Int) = this.transactionHandler.execute {
        cartRepository.deleteByCustomerId(customerId)
    }

    fun deleteCart(customerId: Int, cartId: Int) = this.transactionHandler.execute {
        cartRepository.deleteByCustomerIdAndCartId(customerId, cartId)
    }
}