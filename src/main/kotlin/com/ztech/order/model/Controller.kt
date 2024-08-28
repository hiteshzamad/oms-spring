package com.ztech.order.model

import com.ztech.order.model.domain.*

fun Account.toMap() = mapOf(
    "accountId" to this.id,
    "username" to this.username,
    "email" to this.email,
    "mobile" to this.mobile,
    "customer" to this.customer?.toMap(),
    "seller" to this.seller?.toMap()
)

fun Customer.toMap() = mapOf(
    "customerId" to this.id,
    "name" to this.name
)

fun Seller.toMap() = mapOf(
    "sellerId" to this.id,
    "name" to this.name
)

fun SavedAddress.toMap() = mapOf(
    "addressId" to this.id,
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
    "productId" to this.id,
    "name" to this.name,
    "category" to this.category,
    "measure" to this.measure,
    "size" to this.size
)

fun Inventory.toMap() = mapOf(
    "inventoryId" to this.id,
    "quantity" to this.quantity,
    "price" to this.price,
    "seller" to this.seller?.toMap(),
    "product" to this.product?.toMap()
)

fun Cart.toMap() = mapOf(
    "cartId" to this.id,
    "quantity" to this.quantity,
    "inventory" to this.inventory?.toMap()
)

fun Payment.toMap() = mapOf(
    "method" to this.method
)

fun Tracker.toMap() = mapOf(
    "status" to this.status,
    "date" to this.date
)

fun DeliveryAddress.toMap() = mapOf(
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

fun PurchaseItem.toMap() = mapOf(
    "purchaseItemId" to this.id,
    "quantity" to this.quantity,
    "price" to this.price,
    "seller" to this.seller?.toMap(),
    "product" to this.product?.toMap(),
    "status" to this.trackers?.map { it.toMap() }
)

fun Order.toMap() = mapOf(
    "orderId" to this.id,
    "orderAddress" to this.deliveryAddress?.toMap(),
    "purchaseItems" to this.purchaseItems?.map { it.toMap() },
    "orderPayment" to this.payment?.toMap()
)

fun Checkout.toMap() = mapOf(
    "carts" to this.carts.map { it.toMap() },
    "totalAmount" to this.totalAmount
)
