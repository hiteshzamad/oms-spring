package com.ztech.order.auth

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import java.io.Serializable

class BearerAuthenticationProvider(
    private val bearerAuthenticationService: BearerAuthenticationService
) : AuthenticationProvider, Serializable {

    override fun authenticate(authentication: Authentication): Authentication {
        val auth = authentication as BearerAuthenticationToken
        return bearerAuthenticationService.authenticate(auth.credentials!!)
    }

    override fun supports(authentication: Class<out Any>): Boolean {
        return BearerAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

}