package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.model.common.Measure
import com.ztech.order.repository.ProductRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import com.ztech.order.model.entity.Product as ProductEntity

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
) : AbstractService() {
    fun createProduct(
        name: String, category: String, measure: String, size: Double
    ) = tryCatch {
        val savedEntity = productRepository.save(ProductEntity().also {
            it.name = name
            it.category = category
            it.measure = Measure.valueOf(measure.uppercase())
            it.size = size.toBigDecimal()
        })
        ServiceResponse(Status.SUCCESS, savedEntity.toDomain())
    }

    fun getProductsByName(name: String, page: Int, pageSize: Int) = tryCatch {
        val entities = productRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, pageSize))
        ServiceResponse(Status.SUCCESS, entities.map { it.toDomain() })
    }

    fun getProduct(productId: Int) = tryCatch {
        val entity = productRepository.findByProductId(productId)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

}