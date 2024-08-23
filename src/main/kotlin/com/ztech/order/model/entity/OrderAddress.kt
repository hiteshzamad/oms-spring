package com.ztech.order.model.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "order_address"
)
data class OrderAddress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_address_id")
    val orderAddressId: Int? = null
) {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    lateinit var order: Order

    @Column(name = "name", length = 64, nullable = false, updatable = false)
    lateinit var name: String

    @Column(name = "mobile", length = 15, nullable = false, updatable = false)
    lateinit var mobile: String

    @Column(name = "address_1", length = 255, nullable = false, updatable = false)
    lateinit var address1: String

    @Column(name = "address_2", length = 255, updatable = false)
    var address2: String? = null

    @Column(name = "address_3", length = 255, updatable = false)
    var address3: String? = null

    @Column(name = "city", length = 64, nullable = false, updatable = false)
    lateinit var city: String

    @Column(name = "state", length = 64, nullable = false, updatable = false)
    lateinit var state: String

    @Column(name = "country", length = 64, nullable = false, updatable = false)
    lateinit var country: String

    @Column(name = "pincode", length = 20, nullable = false, updatable = false)
    lateinit var pincode: String
}
