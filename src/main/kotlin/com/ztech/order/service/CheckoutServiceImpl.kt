package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.exception.ResourceInvalidException
import com.ztech.order.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.Checkout as CheckoutDomain

@Service
class CheckoutServiceImpl(
    private val savedAddressService: SavedAddressServiceImpl,
    private val cartService: CartServiceImpl,
    private val inventoryService: InventoryServiceImpl,
    private val orderService: OrderServiceImpl,
    private val transactionHandler: TransactionHandler
) {

    fun checkout(customerId: Int): CheckoutDomain {
        val carts = cartService.getCartsWithInventoryByCustomerId(customerId)
        carts.forEach { cart ->
            if (cart.inventory!!.quantity < cart.quantity)
                throw ResourceInvalidException("Inventory not enough")
        }
        return CheckoutDomain(carts, carts.sumOf { it.inventory!!.price * it.quantity })
    }

    fun processOrder(customerId: Int, savedAddressId: Int, paymentMethod: String) = this.transactionHandler.execute {
        val carts = cartService.getCartsByCustomerId(customerId).ifEmpty {
            throw ResourceNotFoundException("Cart is empty")
        }
        carts.forEach { cart ->
            inventoryService.updateQuantityByInventory(cart.inventory!!, -1 * cart.quantity)
        }
        val savedAddress = savedAddressService.getSavedAddressByCustomerIdAndSavedAddressId(customerId, savedAddressId)
        cartService.deleteCartByCustomerId(customerId)
        orderService.createOrder(customerId, paymentMethod, savedAddress, carts)
    }

    fun revertOrders() = transactionHandler.execute {
        orderService.getExpiredOrders().forEach { order ->
            order.purchaseItems!!.map {item ->
                val inventory = try {
                    inventoryService.getInventoryBySellerIdAndProductId(item.seller!!.id, item.product!!.id)
                } catch (_: ResourceNotFoundException){
                    null
                }
                if (inventory != null){
                    inventoryService.updateQuantityByInventory(inventory, item.quantity)
                }
            }
            orderService.deleteOrder(order.id, order.purchaseItems.map { it.id })
        }
    }
}