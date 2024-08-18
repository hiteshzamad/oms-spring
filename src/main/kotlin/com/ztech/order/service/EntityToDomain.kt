package com.ztech.order.service

import com.ztech.order.model.entity.*

fun Account.toDomain(): com.ztech.order.model.domain.Account {
    return com.ztech.order.model.domain.Account(
        accountId = this.accountId!!,
        username = this.username,
        password = this.password,
        email = this.email,
        mobile = this.mobile,
        createdAt = this.createdAt
    )
}

fun Cart.toDomain(read: Boolean = true) = com.ztech.order.model.domain.Cart(
    cartId = cartId!!,
    customerId = customer.customerId!!,
    inventory = if (read) inventory.toDomain() else null,
    quantity = quantity,
)

fun Customer.toDomain(): com.ztech.order.model.domain.Customer {
    return com.ztech.order.model.domain.Customer(
        customerId = this.customerId!!,
        accountId = this.account.accountId!!,
        name = name,
    )
}

fun Inventory.toDomain(read: Boolean = true) = com.ztech.order.model.domain.Inventory(
    inventoryId = inventoryId!!,
    sellerId = seller.sellerId!!,
    product = if (read) product.toDomain() else null,
    quantity = quantity,
    price = price.toDouble()
)

fun Product.toDomain() = com.ztech.order.model.domain.Product(
    productId = productId!!,
    name = name,
    category = category,
    measure = measure.name.lowercase(),
    size = size.toDouble()
)


fun Seller.toDomain() = com.ztech.order.model.domain.Seller(
    sellerId = this.sellerId!!,
    accountId = this.account.accountId!!,
    name = name,
)


fun SavedAddress.toDomain() = com.ztech.order.model.domain.SavedAddress(
    addressId = savedAddressId!!,
    customerId = customer.customerId!!,
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