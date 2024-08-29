package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.exception.ResourceNotFoundException
import com.ztech.order.model.toDomain
import com.ztech.order.repository.jpa.AccountRepository
import com.ztech.order.util.CryptoAESUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.order.model.entity.Account as AccountEntity

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val transactionHandler: TransactionHandler,
    private val cryptoPassword: CryptoAESUtil,
    private val passwordEncoder: PasswordEncoder
) {
    fun createAccount(
        username: String, password: String, email: String?, mobile: String?
    ) = accountRepository.save(AccountEntity().also {
        it.username = username
        it.password = cryptoPassword.encrypt(passwordEncoder.encode(password))
        it.email = email
        it.mobile = mobile
    }).toDomain(_customer = false, _seller = false)

    fun getAccountByAccountId(accountId: Int) =
        accountRepository.findById(accountId).getOrElse {
            throw ResourceNotFoundException("Account not found")
        }.toDomain()

    fun updateAccount(
        accountId: Int, email: String?, mobile: String?, password: String?
    ) = transactionHandler.execute {
        accountRepository.updateById(accountId, password, email, mobile)
    }
}