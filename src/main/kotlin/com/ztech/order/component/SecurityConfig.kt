package com.ztech.order.component//package com.ztech.order
//
//import com.ztech.order.filter.UsernamePasswordJsonRequestAuthenticationFilter
//import org.springframework.context.annotation.Bean
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//
//class SecurityConfig(
//    private val userDetailsService: UserDetailsService,
//    private val authenticationFilter: UsernamePasswordJsonRequestAuthenticationFilter
//) {
//
//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .csrf { it.disable() }
//            .authorizeHttpRequests {
//                it.requestMatchers("/session").permitAll()
//                it.anyRequest().authenticated()
//            }
//        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
//        return http.build()
//    }
//
//    @Bean
//    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
//        return authenticationConfiguration.authenticationManager
//    }
//
//    @Bean
//    fun passwordEncoder() = BCryptPasswordEncoder()
//}
