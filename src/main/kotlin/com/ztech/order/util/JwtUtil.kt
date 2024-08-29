package com.ztech.order.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.util.*
import java.util.concurrent.TimeUnit


class JwtUtil(jwtTokenKey: String) {

    private val jwtTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtTokenKey));
    private val jwtParser: JwtParser = Jwts.parserBuilder().setSigningKey(this.jwtTokenKey).build()

    private val accessTokenValidity = (60 * 60 * 1000).toLong()

    fun createToken(claims: Map<String, Any?>): String {
        val tokenValidity = Date(Date().time + TimeUnit.MINUTES.toMillis(accessTokenValidity))
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(tokenValidity)
            .signWith(jwtTokenKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getClaims(token: String) = jwtParser.parseClaimsJws(token).body

    fun validateClaims(claims: Claims): Boolean {
        return try {
            claims.expiration.after(Date())
        } catch (e: Exception) {
            throw e
        }
    }
}