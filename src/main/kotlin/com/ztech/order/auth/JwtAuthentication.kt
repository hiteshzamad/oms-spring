package com.ztech.order.auth

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class JwtAuthentication(
    private var authenticated: Boolean, username: String, accountId: Int, customerId: Int?, sellerId: Int?,
) : Authentication {

    private val principal = UserPrincipal(accountId, customerId, sellerId, username, null)
    override fun getName() = principal.username

    override fun getAuthorities() = listOf<GrantedAuthority>()

    override fun getCredentials() = null

    override fun getDetails() = null

    override fun getPrincipal() = principal

    override fun isAuthenticated() = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }
}