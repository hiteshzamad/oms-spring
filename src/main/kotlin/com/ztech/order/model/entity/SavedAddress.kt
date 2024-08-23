package com.ztech.order.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "saved_address")
data class SavedAddress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_address_id")
    val savedAddressId: Int? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    lateinit var customer: Customer

    @Column(name = "name", length = 64, nullable = false)
    lateinit var name: String

    @Column(name = "mobile", length = 15, nullable = false)
    lateinit var mobile: String

    @Column(name = "address_1", length = 255, nullable = false)
    lateinit var address1: String

    @Column(name = "address_2", length = 255)
    var address2: String? = null

    @Column(name = "address_3", length = 255)
    var address3: String? = null

    @Column(name = "city", length = 64, nullable = false)
    lateinit var city: String

    @Column(name = "state", length = 64, nullable = false)
    lateinit var state: String

    @Column(name = "country", length = 64, nullable = false)
    lateinit var country: String

    @Column(name = "pincode", length = 20, nullable = false)
    lateinit var pincode: String
}
