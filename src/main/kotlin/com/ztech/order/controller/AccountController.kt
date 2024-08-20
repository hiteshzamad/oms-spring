package com.ztech.order.controller

import com.ztech.order.core.ControllerResponse
import com.ztech.order.core.responseEntity
import com.ztech.order.model.domain.Account
import com.ztech.order.model.dto.AccountCreateRequest
import com.ztech.order.model.dto.AccountUpdateRequest
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
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @GetMapping("/{accountId}")
    fun getAccount(@PathVariable accountId: Int): ResponseEntity<ControllerResponse> {
        val response = accountService.getAccountByAccountId(accountId)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }

    @PutMapping("/{accountId}")
    fun updateAccount(
        @PathVariable accountId: Int, @RequestBody account: AccountUpdateRequest
    ): ResponseEntity<ControllerResponse> {
        val (email, mobile, password) = account
        val response = accountService.updateAccount(accountId, email, mobile, password)
        return responseEntity(response.status, response.data?.toMap(), response.message)
    }
}