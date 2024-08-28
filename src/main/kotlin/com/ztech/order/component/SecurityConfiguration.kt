package com.ztech.order.component

import com.ztech.order.util.CryptoAES
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import java.util.*

@Configuration
@EnableMethodSecurity
class SecurityConfiguration {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, "/accounts").permitAll()
                it.requestMatchers(HttpMethod.POST, "/sessions").permitAll()
                it.anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
        return httpSecurity.build()
    }


    @Bean
    fun base64Encoder(): Base64.Encoder = Base64.getEncoder()

    @Bean
    fun base64Decoder(): Base64.Decoder = Base64.getDecoder()

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun cryptoPassword(
        secret: SecretConfiguration,
        base64Encoder: Base64.Encoder,
        base64Decoder: Base64.Decoder
    ) = CryptoAES(secret.getPasswordCryptoKey(), base64Encoder, base64Decoder)

}