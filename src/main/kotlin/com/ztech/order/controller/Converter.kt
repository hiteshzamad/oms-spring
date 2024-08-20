package com.ztech.order.controller

import com.ztech.order.model.domain.*

fun Account.toMap() = mapOf(
    "accountId" to this.accountId,
    "username" to this.username,
    "email" to this.email,
    "mobile" to this.mobile
)

fun Customer.toMap() = mapOf(
    "customerId" to this.customerId,
    "name" to this.name
)

fun Seller.toMap() = mapOf(
    "sellerId" to this.sellerId,
    "name" to this.name
)

fun List<SavedAddress>.toMap() = mapOf("savedAddresses" to this.map { it.toMap() })

fun SavedAddress.toMap() = mapOf(
    "addressId" to this.addressId,
    "name" to this.name,
    "mobile" to this.mobile,
    "address1" to this.address1,
    "address2" to this.address2,
    "address3" to this.address3,
    "city" to this.city,
    "state" to this.state,
    "country" to this.country,
    "pincode" to this.pincode
)

fun List<Product>.toMap() = mapOf("products" to this.map { it.toMap() })

fun Product.toMap() = mapOf(
    "productId" to this.productId,
    "name" to this.name,
    "category" to this.category,
    "measure" to this.measure,
    "size" to this.size
)

fun List<Inventory>.toMap() = mapOf("inventories" to this.map { it.toMap() })

fun Inventory.toMap() = mapOf(
    "inventoryId" to this.inventoryId,
    "quantity" to this.quantity,
    "price" to this.price,
    "seller" to this.seller?.toMap(),
    "product" to this.product?.toMap()
)

fun List<Cart>.toMap() = mapOf("carts" to this.map { it.toMap() })

fun Cart.toMap() = mapOf(
    "cartId" to this.cartId,
    "quantity" to this.quantity,
    "inventory" to this.inventory?.toMap()
)

fun OrderPayment.toMap() = mapOf(
    "method" to this.method
)

fun List<OrderStatus>.toMap() = mapOf("orderStatuses" to this.map { it.toMap() })

fun OrderStatus.toMap() = mapOf(
    "status" to this.status,
    "date" to this.date
)

fun OrderAddress.toMap() = mapOf(
    "name" to this.name,
    "mobile" to this.mobile,
    "address1" to this.address1,
    "address2" to this.address2,
    "address3" to this.address3,
    "city" to this.city,
    "state" to this.state,
    "country" to this.country,
    "pincode" to this.pincode
)

fun List<OrderItem>.toMap() = mapOf("orderItems" to this.map { it.toMap() })

fun OrderItem.toMap() = mapOf(
    "quantity" to this.quantity,
    "price" to this.price,
    "seller" to this.seller.toMap(),
    "product" to this.product.toMap()
)

fun Order.toMap() = mapOf(
    "orderId" to this.orderId,
    "orderStatuses" to this.orderStatuses.toMap(),
    "orderAddress" to this.orderAddress.toMap(),
    "orderItems" to this.orderItems.toMap(),
    "orderPayment" to this.orderPayment.toMap()
)


