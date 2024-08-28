package com.ztech.order.service

import com.ztech.order.model.domain.AuthenticationDetails
import com.ztech.order.repository.jpa.AccountRepository
import com.ztech.order.util.CryptoAES
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val accountRepository: AccountRepository,
    private val cryptoPassword: CryptoAES
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val optional = accountRepository.findByUsername(username)
        if (optional.isPresent) {
            val account = optional.get()
            return AuthenticationDetails(
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