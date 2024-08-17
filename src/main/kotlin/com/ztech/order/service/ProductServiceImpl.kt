package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.model.common.Measure
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.repository.ProductRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.domain.Product as ProductDomain
import com.ztech.order.model.entity.Product as ProductEntity

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : AbstractService() {
    fun createProduct(
        name: String, category: String, measure: Measure, size: Double
    ) = tryCatchDaoCall {
        val savedEntity = productRepository.save(ProductEntity().also {
            it.name = name
            it.category = category
            it.measure = measure
            it.size = size.toBigDecimal()
        })
        ServiceResponse(Status.SUCCESS, savedEntity.toDomain())
    }

    fun getProducts() = tryCatchDaoCall {
        val products = productRepository.findAll().map { it.toDomain() }
        ServiceResponse(Status.SUCCESS, products)
    }

    fun getProductsByName(name: String) = tryCatchDaoCall {
        val products = productRepository.findByName(name).map { it.toDomain() }
        ServiceResponse(Status.SUCCESS, products)
    }

    fun getProduct(productId: Int) = tryCatchDaoCall {
        val product = productRepository.findByProductId(productId).toDomain()
        ServiceResponse(Status.SUCCESS, product)
    }

    private fun ProductEntity.toDomain() = ProductDomain(
        name = name, category = category, measure = measure, size = size.toDouble()
    )
}