package com.ztech.order.repository.jpa

import com.ztech.order.model.entity.SavedAddress
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SavedAddressRepository : JpaRepository<SavedAddress, Int> {
    fun findByCustomerId(customerId: Int, pageRequest: PageRequest): List<SavedAddress>
    fun findByIdAndCustomerId(id: Int, customerId: Int): Optional<SavedAddress>

    @Modifying
    @Query(
        "UPDATE SavedAddress s " +
                "SET s.name = :name, s.mobile = :mobile, " +
                "s.address1 = :address1, s.address2 = :address2, s.address3 = :address3, " +
                "s.city = :city, s.state = :state, s.country = :country, s.pincode = :pincode " +
                "WHERE s.id = :addressId AND s.customer.id = :customerId"
    )
    fun updateByIdAndCustomerId(
        @Param("addressId") id: Int, @Param("customerId") customerId: Int,
        @Param("name") name: String, @Param("mobile") mobile: String,
        @Param("address1") address1: String, @Param("address2") address2: String?,
        @Param("address3") address3: String?, @Param("city") city: String,
        @Param("state") state: String, @Param("country") country: String, @Param("pincode") pincode: String
    )

    @Modifying
    @Query("DELETE FROM SavedAddress s WHERE s.id = :addressId AND s.customer.id = :customerId")
    fun deleteByIdAndCustomerId(
        @Param("addressId") id: Int, @Param("customerId") customerId: Int
    )
}