package com.ztech.order.controller

import com.ztech.order.model.dto.AccountCreateRequest
import com.ztech.order.model.dto.AccountUpdateRequest
import com.ztech.order.model.response.Response
import com.ztech.order.model.response.responseSuccess
import com.ztech.order.model.toMap
import com.ztech.order.validator.ValidId
import com.ztech.order.service.AccountServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountService: AccountServiceImpl
) {

    @PostMapping
    fun createAccount(@RequestBody @Valid account: AccountCreateRequest): ResponseEntity<Response> {
        val (username, password, email, mobile) = account
        val response = accountService.createAccount(username, password, email, mobile)
        return responseSuccess(response.toMap())
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("#accountId == authentication.principal.aid")
    fun getAccount(@PathVariable @ValidId accountId: Int): ResponseEntity<Response> {
        val response = accountService.getAccountByAccountId(accountId)
        return responseSuccess(response.toMap())
    }

    @PutMapping("/{accountId}")
    @PreAuthorize("#accountId == authentication.principal.aid")
    fun updateAccount(
        @PathVariable @ValidId accountId: Int,
        @RequestBody @Valid account: AccountUpdateRequest
    ): ResponseEntity<Response> {
        val (email, mobile, password) = account
        accountService.updateAccount(accountId, email, mobile, password)
        return responseSuccess()
    }
}