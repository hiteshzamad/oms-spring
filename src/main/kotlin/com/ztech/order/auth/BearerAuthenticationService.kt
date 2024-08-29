package com.ztech.order.auth

import org.springframework.security.core.Authentication

interface BearerAuthenticationService {
    fun authenticate(token: String): Authentication
}