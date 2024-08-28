package com.ztech.order.controller

import com.ztech.order.exception.AuthenticationException
import com.ztech.order.model.domain.AuthenticationDetails
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sessions")
class SessionController {

    @PostMapping
    fun create(authentication: Authentication?): ResponseEntity<Response> {
        if (authentication == null) throw AuthenticationException()
        val account = authentication.principal as AuthenticationDetails
        return responseSuccess(
            mapOf(
                "accountId" to account.aid,
                "customerId" to account.cid,
                "sellerId" to account.sid
            )
        )
    }

    @DeleteMapping
    fun delete(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ): ResponseEntity<Response> {
        if (authentication == null) throw AuthenticationException()
        SecurityContextLogoutHandler().logout(request, response, authentication)
        request.session.invalidate()
        return responseSuccess()
    }
}