package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.exception.ResourceInvalidException
import com.ztech.order.exception.ResourceNotFoundException
import org.springframework.dao.EmptyResultDataAccessException
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
        val carts = cartService.getCartsByCustomerId(customerId)
        carts.forEach { cart ->
            if (cart.inventory!!.quantity < cart.quantity) {
                throw ResourceInvalidException("Inventory not enough")
            }
        }
        return CheckoutDomain(carts, carts.sumOf { it.inventory!!.price * it.quantity })
    }

    fun processOrder(customerId: Int, savedAddressId: Int, paymentMethod: String) = this.transactionHandler.execute {
        val carts = cartService.getCartsByCustomerId(customerId)
        if (carts.isEmpty()) {
            throw ResourceNotFoundException("Cart is empty")
        }
        carts.forEach { cart ->
            inventoryService.updateQuantityByInventory(cart.inventory!!, -1 * cart.quantity)
        }
        val savedAddress = savedAddressService.getSavedAddressByCustomerIdAndSavedAddressId(customerId, savedAddressId)
        cartService.deleteCartsByCustomerId(customerId)
        orderService.createOrder(customerId, paymentMethod, savedAddress, carts)
    }

    fun revertOrder(orderId: Int) = this.transactionHandler.execute {
        val order = orderService.getOrderByOrderId(orderId)
        order.orderItems.forEach { orderItem ->
            val inventory = try {
                inventoryService.getInventoryBySellerIdAndProductId(
                    orderItem.seller.sellerId,
                    orderItem.product.productId
                )
            } catch (e: EmptyResultDataAccessException) {
                null
            }
            if (inventory != null) {
                this.inventoryService.updateQuantityByInventory(inventory, orderItem.quantity)
                cartService.createCart(order.customerId, inventory.inventoryId, orderItem.quantity)
            }
        }
        orderService.deleteOrder(orderId)
    }

}