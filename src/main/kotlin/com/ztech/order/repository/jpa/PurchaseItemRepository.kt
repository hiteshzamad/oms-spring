package com.ztech.order.repository.jpa

import com.ztech.order.model.entity.PurchaseItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseItemRepository : JpaRepository<PurchaseItem, Int>