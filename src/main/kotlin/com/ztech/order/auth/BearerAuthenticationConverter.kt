package com.ztech.order.auth

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import java.util.*

class BearerAuthenticationConverter(
    private val authenticationDetailsSource: AuthenticationDetailsSource<HttpServletRequest, *> = WebAuthenticationDetailsSource()
) : AuthenticationConverter {
    companion object {
        const val AUTHENTICATION_SCHEME_BEARER = "Bearer"
    }

    override fun convert(request: HttpServletRequest): Authentication? {
        var header = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        header = header.trim { it <= ' ' }
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BEARER)) {
            return null
        }
        if (header.equals(AUTHENTICATION_SCHEME_BEARER, ignoreCase = true)) {
            throw BadCredentialsException("Empty bearer authentication token")
        }
        val token = header.substring(7)
        val result = BearerAuthenticationToken.instance(token)
        result.details = this.authenticationDetailsSource.buildDetails(request)
        return result
    }


    private fun decode(base64Token: ByteArray): ByteArray {
        return try {
            Base64.getDecoder().decode(base64Token)
        } catch (ex: IllegalArgumentException) {
            throw BadCredentialsException("Failed to decode bearer authentication token")
        }
    }

}