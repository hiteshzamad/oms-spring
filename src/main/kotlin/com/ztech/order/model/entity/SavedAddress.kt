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
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    lateinit var customer: Customer

    @Column(name = "name", nullable = false, length = 64)
    lateinit var name: String

    @Column(name = "mobile", nullable = false, length = 15)
    lateinit var mobile: String

    @Column(name = "address_1", nullable = false, length = 255)
    lateinit var address1: String

    @Column(name = "address_2", length = 255)
    var address2: String? = null

    @Column(name = "address_3", length = 255)
    var address3: String? = null

    @Column(name = "city", nullable = false, length = 64)
    lateinit var city: String

    @Column(name = "state", nullable = false, length = 64)
    lateinit var state: String

    @Column(name = "country", nullable = false, length = 64)
    lateinit var country: String

    @Column(name = "pincode", nullable = false, length = 20)
    lateinit var pincode: String
}
