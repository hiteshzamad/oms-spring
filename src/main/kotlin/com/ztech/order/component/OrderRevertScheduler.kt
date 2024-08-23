package com.ztech.order.component

import com.ztech.order.service.CheckoutServiceImpl
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OrderRevertScheduler(
    private val checkoutService: CheckoutServiceImpl
) {

    @Scheduled(fixedRate = 60000)
    fun revertOrders() {
        checkoutService.revertOrders()
    }

}