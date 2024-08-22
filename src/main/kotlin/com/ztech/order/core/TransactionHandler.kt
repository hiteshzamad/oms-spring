package com.ztech.order.core

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionHandler {
    @Transactional
    fun <T> execute(block: () -> ServiceResponse<T>): ServiceResponse<T> {
        return block()
    }
}