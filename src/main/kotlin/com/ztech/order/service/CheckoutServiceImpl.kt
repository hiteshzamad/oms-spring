package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.core.TransactionHandler
import com.ztech.order.model.common.OrderStatusType
import com.ztech.order.model.common.PaymentMethod
import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.repository.*
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.Checkout as CheckoutDomain
import com.ztech.order.model.entity.Customer as CustomerEntity
import com.ztech.order.model.entity.Inventory as InventoryEntity
import com.ztech.order.model.entity.Order as OrderEntity
import com.ztech.order.model.entity.OrderAddress as OrderAddressEntity
import com.ztech.order.model.entity.OrderItem as OrderItemEntity
import com.ztech.order.model.entity.OrderPayment as OrderPaymentEntity
import com.ztech.order.model.entity.OrderStatus as OrderStatusEntity
import com.ztech.order.model.entity.Product as ProductEntity
import com.ztech.order.model.entity.Seller as SellerEntity

@Service
class CheckoutServiceImpl(
    private val savedAddressRepository: SavedAddressRepository,
    private val cartRepository: CartRepository,
    private val inventoryRepository: InventoryRepository,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val orderAddressRepository: OrderAddressRepository,
    private val orderStatusRepository: OrderStatusRepository,
    private val orderPaymentRepository: OrderPaymentRepository,
    private val transactionHandler: TransactionHandler
) : AbstractService() {

    fun checkout(customerId: Int) = tryCatch {
        val carts = cartRepository.findByCustomerCustomerId(customerId)
        carts.forEach { cart ->
            if (cart.inventory.quantity < cart.quantity) {
                return@tryCatch ServiceResponse<CheckoutDomain>(Status.INVALID_INPUT, null, "Inventory not available")
            }
        }
        ServiceResponse(
            Status.SUCCESS,
            CheckoutDomain(carts.map { it.toDomain() }, carts.sumOf { it.inventory.price.toDouble() * it.quantity })
        )
    }

    fun createOrder(customerId: Int, savedAddressId: Int, paymentMethod: String) = tryCatch {
        this.transactionHandler.execute {
            val carts = cartRepository.findByCustomerCustomerId(customerId)
            carts.forEach { cart ->
                if (cart.inventory.quantity < cart.quantity) {
                    throw RuntimeException("Inventory not available")
                }
            }
            val order = orderRepository.save(OrderEntity().also {
                it.customer = CustomerEntity(customerId)
            })
            val orderStatus = orderStatusRepository.save(OrderStatusEntity().also {
                it.order = order
                it.status = OrderStatusType.CREATED
            })
            val orderAddress = orderAddressRepository.save(OrderAddressEntity().also {
                val savedAddress =
                    savedAddressRepository.findByCustomerCustomerIdAndSavedAddressId(customerId, savedAddressId)
                it.order = order
                it.name = savedAddress.name
                it.mobile = savedAddress.mobile
                it.address1 = savedAddress.address1
                it.address2 = savedAddress.address2
                it.address3 = savedAddress.address3
                it.city = savedAddress.city
                it.state = savedAddress.state
                it.country = savedAddress.country
                it.pincode = savedAddress.pincode
            })
            val orderItems = carts.map { cart ->
                inventoryRepository.save(
                    InventoryEntity(cart.inventory.inventoryId).also {
                        it.product = cart.inventory.product
                        it.seller = cart.inventory.seller
                        it.quantity = cart.inventory.quantity - cart.quantity
                        it.price = cart.inventory.price
                    }
                )
                orderItemRepository.save(OrderItemEntity().also {
                    it.order = order
                    it.quantity = cart.quantity
                    it.price = cart.inventory.price
                    it.seller = SellerEntity(cart.inventory.seller.sellerId)
                    it.product = ProductEntity(cart.inventory.product.productId)
                })
            }
            val orderPayment = orderPaymentRepository.save(OrderPaymentEntity().also {
                it.order = order
                it.status = PaymentStatus.PENDING
                it.method = PaymentMethod.valueOf(paymentMethod.uppercase())
                it.amount = orderItems.sumOf { item -> (item.price * item.quantity.toBigDecimal()) }
            })
            ServiceResponse(Status.SUCCESS, order.toDomain(orderAddress, orderPayment, orderItems, listOf(orderStatus)))
        }
    }

}