package com.ztech.order.auth

import com.ztech.order.exception.ExpiredTokenException
import com.ztech.order.exception.InvalidTokenException
import com.ztech.order.util.JwtUtil
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.core.Authentication

class JwtAuthenticationService(
    private val jwtUtil: JwtUtil
) : BearerAuthenticationService {

    override fun authenticate(token: String): Authentication {
        try {
            val claim = jwtUtil.getClaims(token)
            return JwtAuthentication(
                authenticated = true,
                username = claim["username"] as String,
                accountId = claim["accountId"] as Int,
                customerId = claim["customerId"] as Int?,
                sellerId = claim["sellerId"] as Int?,
            )
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (e: Exception) {
            println(e.message)
            throw InvalidTokenException()
        }
    }
}