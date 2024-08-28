package com.ztech.order.repository.jpa

import com.ztech.order.model.entity.Product
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int> {
    fun findByNameContainingIgnoreCase(name: String, pageRequest: PageRequest): List<Product>
}
