package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.repository.AccountRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.Account as AccountDomain
import com.ztech.order.model.entity.Account as AccountEntity

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository
) : AbstractService() {
    fun createAccount(
        username: String, password: String, email: String?, mobile: String?
    ) = tryCatchDaoCall {
        val responseGetAccount = getAccountByUsername(username)
        when (responseGetAccount.status) {
            Status.SUCCESS -> ServiceResponse(Status.CONFLICT)
            Status.NOT_FOUND -> {
                val savedEntity = accountRepository.save(AccountEntity().also {
                    it.username = username
                    it.password = password
                    it.email = email
                    it.mobile = mobile
                })
                ServiceResponse(Status.SUCCESS, savedEntity.toDomain())
            }
            else -> responseGetAccount
        }
    }

    fun getAccountByAccountId(accountId: Int) = tryCatchDaoCall {
        ServiceResponse(
            Status.SUCCESS, accountRepository.findByAccountId(accountId).toDomain()
        )
    }

    fun getAccountByUsername(username: String) = tryCatchDaoCall {
        ServiceResponse(Status.SUCCESS, accountRepository.findByUsername(username).toDomain())
    }

    fun updateAccount(
        accountId: Int, email: String?, mobile: String?, password: String?
    ) = tryCatchDaoCall {
        val responseGetAccount = getAccountByAccountId(accountId)
        when (responseGetAccount.status) {
            Status.SUCCESS -> responseGetAccount.data!!.let { account ->
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
            else -> responseGetAccount
        }
    }

    private fun AccountEntity.toDomain(): AccountDomain {
        return AccountDomain(
            accountId = this.accountId!!,
            username = this.username,
            password = this.password,
            email = this.email,
            mobile = this.mobile,
            createdAt = this.createdAt
        )
    }
}