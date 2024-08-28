package com.ztech.order.repository.jpa

import com.ztech.order.model.entity.DeliveryAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DeliveryAddressRepository : JpaRepository<DeliveryAddress, Int> {

    @Modifying
    @Query("DELETE FROM DeliveryAddress da WHERE da.order.id = :orderId")
    fun deleteByOrderId(@Param("orderId") orderId: Int)
}
