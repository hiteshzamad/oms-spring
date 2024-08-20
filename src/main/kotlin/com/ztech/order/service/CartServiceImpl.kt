package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.repository.CartRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.ztech.order.model.domain.Cart as CartDomain
import com.ztech.order.model.entity.Cart as CartEntity
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.Inventory as InventoryEntity

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository
) : AbstractService() {
    fun createCart(customerId: Int, inventoryId: Int, quantity: Int) = tryCatchDaoCall {
        ServiceResponse(
            Status.SUCCESS, cartRepository.save(CartEntity().also { cart ->
                cart.customer = CustomerEntity(customerId)
                cart.inventory = InventoryEntity(inventoryId)
                cart.quantity = quantity
            }).toDomain(false)
        )
    }

    fun getCartsByCustomerId(customerId: Int) = tryCatchDaoCall {
        ServiceResponse(Status.SUCCESS,
            cartRepository.findByCustomerCustomerId(customerId).map { it.toDomain() })
    }

    fun getCartByCustomerIdAndCartId(customerId: Int, cartId: Int) = tryCatchDaoCall {
        ServiceResponse(
            Status.SUCCESS, cartRepository.findByCustomerCustomerIdAndCartId(customerId, cartId).toDomain()
        )
    }

    fun updateCart(customerId: Int, cartId: Int, quantity: Int) = tryCatchDaoCall {
        val responseGetCart = getCartByCustomerIdAndCartId(customerId, cartId)
        when (responseGetCart.status) {
            Status.SUCCESS -> responseGetCart.data!!.let { cart ->
                val newEntity = CartEntity(cart.cartId)
                newEntity.customer = CustomerEntity(cart.cartId)
                newEntity.inventory = InventoryEntity(cart.inventory!!.inventoryId)
                newEntity.quantity = quantity
                val updatedEntity = cartRepository.save(newEntity)
                ServiceResponse(Status.SUCCESS, updatedEntity.toDomain())
            }
            else -> responseGetCart
        }
    }

    @Transactional
    fun deleteCart(customerId: Int, cartId: Int) = tryCatchDaoCall {
        cartRepository.deleteByCustomerCustomerIdAndCartId(customerId, cartId)
        ServiceResponse<CartDomain>(Status.SUCCESS)
    }

}