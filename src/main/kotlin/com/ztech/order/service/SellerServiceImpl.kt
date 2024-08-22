package com.ztech.order.service

import com.ztech.order.core.AbstractService
import com.ztech.order.core.ServiceResponse
import com.ztech.order.core.Status
import com.ztech.order.model.entity.Account
import com.ztech.order.repository.SellerRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.entity.Seller as SellerEntity

@Service
class SellerServiceImpl(
    private val sellerRepository: SellerRepository,
) : AbstractService() {

    fun createSeller(accountId: Int, name: String) = tryCatch {
        val savedEntity = sellerRepository.save(SellerEntity().also {
            it.account = Account(accountId)
            it.name = name
        })
        ServiceResponse(Status.SUCCESS, savedEntity.toDomain())
    }

    fun getSellerByAccountId(accountId: Int) = tryCatch {
        val entity = sellerRepository.findByAccountAccountId(accountId)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

    fun getSellerBySellerId(sellerId: Int) = tryCatch {
        val entity = sellerRepository.findBySellerId(sellerId)
        ServiceResponse(Status.SUCCESS, entity.toDomain())
    }

}