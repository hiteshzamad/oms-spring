package com.ztech.order.auth

import org.springframework.security.authentication.AbstractAuthenticationToken

class BearerAuthenticationToken(private var token: String?) : AbstractAuthenticationToken(null) {

    companion object {

        fun instance(token: String): BearerAuthenticationToken {
            return BearerAuthenticationToken(token)
        }
    }

    init {
        super.setAuthenticated(false)
    }

    override fun getCredentials() = this.token

    override fun getPrincipal() = null

    override fun eraseCredentials() {
        super.eraseCredentials()
        token = null
    }

}