package com.ztech.order.repository

import com.ztech.order.model.common.PaymentStatus
import com.ztech.order.model.entity.Order
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, Int> {

    @EntityGraph("OrderWithCustomerAndAddressAndPaymentAndPurchaseItemsWithProductAndSellerAndTrackers")
    fun findByIdAndCustomerId(id: Int, customerId: Int): Optional<Order>

    @EntityGraph("OrderWithAddressAndPaymentAndPurchaseItemsWithProduct")
    fun findByCustomerId(customerId: Int): List<Order>

    @EntityGraph("OrderWithAddressAndPaymentAndPurchaseItemsWithProduct")
    fun findByPurchaseItemsSellerIdAndPaymentStatus(sellerId: Int, paymentStatus: PaymentStatus): List<Order>

    @EntityGraph("OrderWithAddressAndPaymentAndPurchaseItemsWithProductAndTrackers")
    fun findByIdAndPurchaseItemsSellerIdAndPaymentStatus(
        id: Int, sellerId: Int, paymentStatus: PaymentStatus
    ): Optional<Order>

    @EntityGraph("OrderWithAddressAndPaymentAndPurchaseItemsWithProductAndSeller")
    fun findByCreatedAtLessThanAndPaymentStatus(createdAt: LocalDateTime, paymentStatus: PaymentStatus): List<Order>

    @EntityGraph("OrderWithAddressAndPaymentAndPurchaseItemsWithProductAndTrackers")
    fun findByPurchaseItemsIdAndPurchaseItemsSellerIdAndPaymentStatus(
        purchaseItemsId: Int, sellerId: Int, paymentStatus: PaymentStatus,
    ): Optional<Order>

}
