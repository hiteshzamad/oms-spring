package com.ztech.order.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "account")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val accountId: Int? = null,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Column(name = "username", nullable = false, unique = true, length = 32)
    lateinit var username: String

    @Column(name = "password", nullable = false, length = 255)
    lateinit var password: String

    @Column(name = "email", unique = true, length = 64)
    var email: String? = null

    @Column(name = "mobile", unique = true, length = 15)
    var mobile: String? = null

}
