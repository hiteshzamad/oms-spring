package com.ztech.order.controller

import com.ztech.order.auth.DaoAuthentication
import com.ztech.order.exception.AuthenticationException
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tokens")
class TokenController(
    private val jwtUtil: JwtUtil
) {

    @PostMapping
    fun create(authentication: Authentication?): ResponseEntity<Response> {
        if (authentication == null) throw AuthenticationException()
        val account = authentication.principal as DaoAuthentication
        val map = mapOf(
            "username" to account.username,
            "accountId" to account.aid,
            "customerId" to account.cid,
            "sellerId" to account.sid,
        )
        val token: String = jwtUtil.createToken(map)
        return responseSuccess(mapOf("token" to token))
    }
}