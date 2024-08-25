package com.ztech.order.model

import com.ztech.order.model.entity.*

fun Account.toDomain() = com.ztech.order.model.domain.Account(
    id = id!!,
    username = this.username,
    password = this.password,
    email = this.email,
    mobile = this.mobile,
    createdAt = this.createdAt
)

fun Customer.toDomain() = com.ztech.order.model.domain.Customer(
    id = id!!,
    name = name,
)

fun Seller.toDomain() = com.ztech.order.model.domain.Seller(
    id = id!!,
    name = name,
)

fun Product.toDomain() = com.ztech.order.model.domain.Product(
    id = id!!,
    name = name,
    category = category,
    measure = measure.name.lowercase(),
    size = size.toDouble()
)

fun SavedAddress.toDomain() = com.ztech.order.model.domain.SavedAddress(
    id = id!!,
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

fun Inventory.toDomain(_product: Boolean = true, _seller: Boolean = true) = com.ztech.order.model.domain.Inventory(
    id = id!!,
    quantity = quantity,
    price = price.toDouble(),
    product = if (_product) this.product.toDomain() else null,
    seller = if (_seller) this.seller.toDomain() else null,
)

fun Cart.toDomain(_inventory: Boolean = true, _product: Boolean = true, _seller: Boolean = true) =
    com.ztech.order.model.domain.Cart(
        id = id!!,
        quantity = quantity,
        inventory = if (_inventory) this.inventory.toDomain(_product, _seller) else null,
    )

fun PurchaseItem.toDomain(
    _product: Boolean = true, _seller: Boolean = true, _tracker: Boolean = true
) = com.ztech.order.model.domain.PurchaseItem(
    id = id!!,
    quantity = quantity,
    price = price.toDouble(),
    product = if (_product) product.toDomain() else null,
    seller = if (_seller) seller.toDomain() else null,
    trackers = if (_tracker) trackers.map { it.toDomain() } else null,
)

fun DeliveryAddress.toDomain() = com.ztech.order.model.domain.DeliveryAddress(
    id = id!!,
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

fun Tracker.toDomain() = com.ztech.order.model.domain.Tracker(
    id = id!!,
    date = date,
    status = status.name.lowercase(),
)

fun Payment.toDomain() = com.ztech.order.model.domain.Payment(
    id = id!!,
    status = status.name.lowercase(),
    method = method.name.lowercase(),
    amount = amount.toDouble(),
)

fun Order.toDomain(
    _payment: Boolean = true,
    _product: Boolean = true,
    _seller: Boolean = true,
    _address: Boolean = true,
    _item: Boolean = true,
    _tracker: Boolean = true
) = com.ztech.order.model.domain.Order(
    id = id!!,
    customerId = customer.id!!,
    payment = if (_payment) payment.toDomain() else null,
    deliveryAddress = if (_address) address.toDomain() else null,
    purchaseItems = if (_item) purchaseItems.map { it.toDomain(_product, _seller, _tracker) } else null,
)