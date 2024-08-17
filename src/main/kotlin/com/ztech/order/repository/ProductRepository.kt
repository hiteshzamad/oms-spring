package com.ztech.order.repository

import com.ztech.order.model.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int> {
    fun findByProductId(productId: Int): Product
    fun findByName(name: String): List<Product>
}
