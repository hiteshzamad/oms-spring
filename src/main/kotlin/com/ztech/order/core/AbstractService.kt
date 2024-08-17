package com.ztech.order.core

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException

abstract class AbstractService {
    fun <T> tryCatchDaoCall(block: () -> ServiceResponse<T>): ServiceResponse<T> {
        return try {
            block()
        } catch (e: EmptyResultDataAccessException) {
            ServiceResponse(Status.NOT_FOUND)
        } catch (e: DataIntegrityViolationException) {
            ServiceResponse(Status.INVALID_INPUT)
        } catch (e: Exception) {
            ServiceResponse(Status.ERROR)
        }
    }
}