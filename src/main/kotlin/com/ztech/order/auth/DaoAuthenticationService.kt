package com.ztech.order.auth

import com.ztech.order.repository.jpa.AccountRepository
import com.ztech.order.util.CryptoAESUtil
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class DaoAuthenticationService(
    private val accountRepository: AccountRepository,
    private val cryptoPassword: CryptoAESUtil
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val optional = accountRepository.findByUsername(username)
        if (optional.isPresent) {
            val account = optional.get()
            return DaoAuthentication(
                aid = account.id!!,
                cid = account.customer?.id,
                sid = account.seller?.id,
                username = account.username,
                password = cryptoPassword.decrypt(account.password),
            )
        } else {
            throw UsernameNotFoundException("Invalid username or password")
        }
    }
}