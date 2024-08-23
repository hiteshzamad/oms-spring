package com.ztech.order.repository

import com.ztech.order.model.entity.OrderAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderAddressRepository : JpaRepository<OrderAddress, Int> {

    @Modifying
    @Query("DELETE FROM OrderAddress oa WHERE oa.order.orderId = :orderId")
    fun deleteByOrderId(orderId: Int)
}
