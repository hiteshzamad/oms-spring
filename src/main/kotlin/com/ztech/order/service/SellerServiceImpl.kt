package com.ztech.order.service

import com.ztech.order.model.entity.Account
import com.ztech.order.model.toDomain
import com.ztech.order.repository.SellerRepository
import org.springframework.stereotype.Service
import com.ztech.order.model.entity.Seller as SellerEntity

@Service
class SellerServiceImpl(
    private val sellerRepository: SellerRepository,
) {

    fun createSeller(accountId: Int, name: String) =
        sellerRepository.save(SellerEntity().also {
            it.account = Account(accountId)
            it.name = name
        }).toDomain()

    fun getSellerByAccountId(accountId: Int) =
        sellerRepository.findByAccountAccountId(accountId).toDomain()

    fun getSellerBySellerId(sellerId: Int) =
        sellerRepository.findBySellerId(sellerId).toDomain()

}