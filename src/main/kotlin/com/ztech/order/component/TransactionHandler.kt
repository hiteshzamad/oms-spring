package com.ztech.order.component

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionHandler {
    @Transactional
    fun <T> execute(block: () -> T): T {
        return block()
    }
}