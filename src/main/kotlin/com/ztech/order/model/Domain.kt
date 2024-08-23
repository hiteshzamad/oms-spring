package com.ztech.order.model

import com.ztech.order.model.entity.*

fun Account.toDomain() = com.ztech.order.model.domain.Account(
    accountId = this.accountId!!,
    username = this.username,
    password = this.password,
    email = this.email,
    mobile = this.mobile,
    createdAt = this.createdAt
)

fun Customer.toDomain() = com.ztech.order.model.domain.Customer(
    customerId = this.customerId!!,
    name = name,
)

fun Seller.toDomain() = com.ztech.order.model.domain.Seller(
    sellerId = this.sellerId!!,
    name = name,
)

fun Product.toDomain() = com.ztech.order.model.domain.Product(
    productId = productId!!,
    name = name,
    category = category,
    measure = measure.name.lowercase(),
    size = size.toDouble()
)

fun SavedAddress.toDomain() = com.ztech.order.model.domain.SavedAddress(
    addressId = savedAddressId!!,
    name = name,
    mobile = mobile,
    address1 = address1,
    address2 = address2,
    address3 = address3,
    city = city,
    state = state,
    country = country,
    pincode = pincode,
)

fun Inventory.toDomain(product: Boolean = true, seller: Boolean = true) = com.ztech.order.model.domain.Inventory(
    inventoryId = inventoryId!!,
    quantity = quantity,
    price = price.toDouble(),
    product = if (product) this.product.toDomain() else null,
    seller = if (seller) this.seller.toDomain() else null,
)

fun Cart.toDomain(inventory: Boolean = true) = com.ztech.order.model.domain.Cart(
    cartId = cartId!!,
    quantity = quantity,
    inventory = if (inventory) this.inventory.toDomain() else null,
)

fun OrderItem.toDomain() = com.ztech.order.model.domain.OrderItem(
    orderItemId = orderItemId!!,
    quantity = quantity,
    price = price.toDouble(),
    product = product.toDomain(),
    seller = seller.toDomain(),
)

fun OrderAddress.toDomain() = com.ztech.order.model.domain.OrderAddress(
    addressId = orderAddressId!!,
    name = name,
    mobile = mobile,
    address1 = address1,
    address2 = address2,
    address3 = address3,
    city = city,
    state = state,
    country = country,
    pincode = pincode,
)

fun OrderStatus.toDomain() = com.ztech.order.model.domain.OrderStatus(
    orderStatusId = orderStatusId!!,
    date = date,
    status = status.name.lowercase(),
)

fun OrderPayment.toDomain() = com.ztech.order.model.domain.OrderPayment(
    paymentId = orderPaymentId!!,
    status = status.name.lowercase(),
    method = method.name.lowercase(),
    amount = amount.toDouble(),
)

fun Order.toDomain() = com.ztech.order.model.domain.Order(
    orderId = orderId!!,
    customerId = customer.customerId!!,
    orderPayment = orderPayment.toDomain(),
    orderAddress = orderAddress.toDomain(),
    orderItems = orderItems.map { it.toDomain() },
    orderStatuses = orderStatuses.map { it.toDomain() }
)