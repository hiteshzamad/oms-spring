package com.ztech.order.repository

import com.ztech.order.model.entity.Tracker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TrackerRepository : JpaRepository<Tracker, Int> {

    @Modifying
    @Query("DELETE FROM Tracker t WHERE t.purchaseItem.id IN :purchaseItemIds")
    fun deleteByPurchaseItemId(@Param("purchaseItemIds") purchaseItemIds: List<Int>)
}