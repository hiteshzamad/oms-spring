package com.ztech.order.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class BearerAuthenticationFilter(
    private val authenticationManager: AuthenticationManager,
) : OncePerRequestFilter() {
    private val authenticationConverter: AuthenticationConverter = BearerAuthenticationConverter()
    private val securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy()
    private val securityContextRepository: SecurityContextRepository = RequestAttributeSecurityContextRepository()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        try {
            val authRequest = this.authenticationConverter.convert(request)
            if (authRequest == null) {
                chain.doFilter(request, response)
                return
            }
            val authResult = authenticationManager.authenticate(authRequest)
            val context = securityContextHolderStrategy.createEmptyContext()
            context.authentication = authResult
            securityContextHolderStrategy.context = context
            this.securityContextRepository.saveContext(context, request, response)
        } catch (ex: AuthenticationException) {
            securityContextHolderStrategy.clearContext()
            chain.doFilter(request, response)
            return
        }
        chain.doFilter(request, response)
    }
}