package com.ztech.order.core

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException

abstract class AbstractService {
    fun <T> tryCatch(block: () -> ServiceResponse<T>): ServiceResponse<T> {
        return try {
            block()
        } catch (e: EmptyResultDataAccessException) {
            ServiceResponse(Status.NOT_FOUND)
        } catch (e: DataIntegrityViolationException) {
            when {
                e.message!!.contains("DUPLICATE", ignoreCase = true)
                        || e.message!!.contains("UNIQUE", ignoreCase = true) ->
                    ServiceResponse(
                        Status.CONFLICT,
                        message = e.message?.split("unicst_")?.get(1)
                            ?.substringBefore("'")?.run { "Duplicate $this" }
                    )
                else -> ServiceResponse(Status.INVALID_INPUT)
            }
        } catch (e: Exception) {
            println(e.message)
            ServiceResponse(Status.ERROR)
        }
    }
}