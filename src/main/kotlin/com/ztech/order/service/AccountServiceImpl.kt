package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.core.TransactionHandler
import com.ztech.order.repository.AccountRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.order.model.entity.Account as AccountEntity

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val transactionHandler: TransactionHandler
) : AbstractService() {
    fun createAccount(
        username: String, password: String, email: String?, mobile: String?
    ) = tryCatch {
        val savedEntity = accountRepository.save(AccountEntity().also {
            it.username = username
            it.password = password
            it.email = email
            it.mobile = mobile
        })
        ServiceResponse(Status.SUCCESS, savedEntity.toDomain())
    }

    fun getAccountByAccountId(accountId: Int) = tryCatch {
        val entity = accountRepository.findByAccountId(accountId)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

    fun getAccountByUsername(username: String) = tryCatch {
        val entity = accountRepository.findByUsername(username)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

    fun updateAccount(
        accountId: Int, email: String?, mobile: String?, password: String?
    ) = tryCatch {
        this.transactionHandler.execute {
            val account = accountRepository.findById(accountId).getOrElse {
                return@execute ServiceResponse(Status.NOT_FOUND, null, "Account not found")
            }
            val newEntity = AccountEntity(account.accountId, account.createdAt)
            newEntity.username = account.username
            newEntity.password = account.password
            newEntity.email = account.email
            newEntity.mobile = account.mobile
            email?.let { newEntity.email = it }
            mobile?.let { newEntity.mobile = it }
            password?.let { newEntity.password = it }
            val updatedEntity = accountRepository.save(newEntity)
            ServiceResponse(Status.SUCCESS, updatedEntity.toDomain())
        }
    }
}