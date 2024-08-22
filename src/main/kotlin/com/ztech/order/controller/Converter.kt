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

fun Product.toMap() = mapOf(
    "productId" to this.productId,
    "name" to this.name,
    "category" to this.category,
    "measure" to this.measure,
    "size" to this.size
)

fun Inventory.toMap() = mapOf(
    "inventoryId" to this.inventoryId,
    "quantity" to this.quantity,
    "price" to this.price,
    "seller" to this.seller?.toMap(),
    "product" to this.product?.toMap()
)

fun Cart.toMap() = mapOf(
    "cartId" to this.cartId,
    "quantity" to this.quantity,
    "inventory" to this.inventory?.toMap()
)

fun OrderPayment.toMap() = mapOf(
    "method" to this.method
)

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

fun OrderItem.toMap() = mapOf(
    "quantity" to this.quantity,
    "price" to this.price,
    "seller" to this.seller.toMap(),
    "product" to this.product.toMap()
)

fun Order.toMap() = mapOf(
    "orderId" to this.orderId,
    "orderStatuses" to this.orderStatuses.map { it.toMap() },
    "orderAddress" to this.orderAddress.toMap(),
    "orderItems" to this.orderItems.map { it.toMap() },
    "orderPayment" to this.orderPayment.toMap()
)

fun Checkout.toMap() = mapOf(
    "carts" to this.carts.map { it.toMap() },
    "totalAmount" to this.totalAmount
)