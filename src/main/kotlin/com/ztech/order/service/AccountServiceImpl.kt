package com.ztech.order.service

import com.ztech.order.component.TransactionHandler
import com.ztech.order.exception.ResourceNotFoundException
import com.ztech.order.model.toDomain
import com.ztech.order.repository.AccountRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.order.model.entity.Account as AccountEntity

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val transactionHandler: TransactionHandler
) {
    fun createAccount(
        username: String, password: String, email: String?, mobile: String?
    ) = accountRepository.save(AccountEntity().also {
        it.username = username
        it.password = password
        it.email = email
        it.mobile = mobile
    }).toDomain()

    fun getAccountByAccountId(accountId: Int) =
        accountRepository.findById(accountId).getOrElse {
            throw ResourceNotFoundException("Account not found")
        }.toDomain()

//    fun getAccountByUsername(username: String) =
//        accountRepository.findByUsername(username).getOrElse {
//            throw ResourceNotFoundException("Account not found")
//        }.toDomain()

    fun updateAccount(
        accountId: Int, email: String?, mobile: String?, password: String?
    ) = transactionHandler.execute {
        accountRepository.updateById(accountId, password, email, mobile)
    }
}