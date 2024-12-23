package com.ztech.order.service

import com.ztech.order.exception.ResourceNotFoundException
import com.ztech.order.model.common.Measure
import com.ztech.order.model.toDomain
import com.ztech.order.repository.jpa.ProductRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import com.ztech.order.model.entity.Product as ProductEntity

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
) {
    fun createProduct(
        name: String, category: String, measure: String, size: Double
    ) = productRepository.save(ProductEntity().also {
        it.name = name
        it.category = category
        it.measure = Measure.valueOf(measure.uppercase())
        it.size = size.toBigDecimal()
    }).toDomain()

    fun getProductsByName(name: String, page: Int, pageSize: Int) =
        productRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, pageSize))
            .map { it.toDomain() }

    fun getProduct(productId: Int) =
        productRepository.findById(productId).getOrElse {
            throw ResourceNotFoundException("Product not found")
        }.toDomain()

}