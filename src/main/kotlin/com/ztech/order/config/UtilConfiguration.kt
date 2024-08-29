package com.ztech.order.config

import com.ztech.order.util.CryptoAESUtil
import com.ztech.order.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class UtilConfiguration {

    @Bean
    fun cryptoAESUtil(
        secret: SecretConfiguration,
    ) = CryptoAESUtil(secret.getPasswordCryptoKey())

    @Bean
    fun jwtUtil(
        secret: SecretConfiguration,
    ) = JwtUtil(secret.getJwtTokenKey())

}