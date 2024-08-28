package com.ztech.order.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@NamedEntityGraph(
    name = "AccountWithCustomerAndSeller",
    attributeNodes = [
        NamedAttributeNode("customer"),
        NamedAttributeNode("seller"),
    ]
)
@Table(
    name = "account", uniqueConstraints = [
        UniqueConstraint(name = "unicst_username", columnNames = ["username"]),
        UniqueConstraint(name = "unicst_mobile", columnNames = ["mobile"]),
        UniqueConstraint(name = "unicst_email", columnNames = ["email"])
    ]
)
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val id: Int? = null,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Column(name = "username", length = 32, nullable = false, updatable = false)
    lateinit var username: String

    @Column(name = "password", length = 1024, nullable = false)
    lateinit var password: String

    @Column(name = "email", length = 64)
    var email: String? = null

    @Column(name = "mobile", length = 15)
    var mobile: String? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    var customer: Customer? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    var seller: Seller? = null

}
