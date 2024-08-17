package com.ztech.order.controller

import com.ztech.order.core.Status
import com.ztech.order.model.dto.AccountCreateRequest
import com.ztech.order.model.dto.AccountUpdateRequest
import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.service.AccountServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountService: AccountServiceImpl
) {

    @PostMapping
    fun createAccount(@RequestBody account: AccountCreateRequest): ResponseEntity<ControllerResponse> {
        val (username, password, email, mobile) = account
        val response = accountService.createAccount(username, password, email, mobile)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status
            ) else responseEntity(status)
        }
    }

    @GetMapping("/{accountId}")
    fun getAccount(@PathVariable accountId: Int): ResponseEntity<ControllerResponse> {
        val response = accountService.getAccountByAccountId(accountId)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "accountId" to data!!.accountId,
                    "username" to data.username,
                    "email" to data.email,
                    "mobile" to data.mobile
                )
            ) else responseEntity(status)
        }
    }

    @PutMapping("/{accountId}")
    fun updateAccount(
        @PathVariable accountId: Int, @RequestBody account: AccountUpdateRequest
    ): ResponseEntity<ControllerResponse> {
        val (email, mobile, password) = account
        val response = accountService.updateAccount(accountId, email, mobile, password)
        with(response) {
            return if (status == Status.SUCCESS) responseEntity(
                status, mapOf(
                    "accountId" to data!!.accountId,
                    "username" to data.username,
                    "email" to data.email,
                    "mobile" to data.mobile
                )
            ) else responseEntity(status)
        }
    }
}