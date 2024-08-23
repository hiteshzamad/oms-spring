package com.ztech.order.component

import com.ztech.order.exception.RequestInvalidException
import com.ztech.order.exception.ResourceConflictException
import com.ztech.order.exception.ResourceInvalidException
import com.ztech.order.exception.ResourceNotFoundException
import com.ztech.order.model.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionConfig : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handleException(e: Exception): ResponseEntity<Any>? {
        println("Exception: ${e.message} $e")
        return when (e) {
            is ResourceNotFoundException -> ResponseEntity(Response(null, e.message), HttpStatus.NOT_FOUND)
            is ResourceConflictException -> ResponseEntity(Response(null, e.message), HttpStatus.CONFLICT)
            is ResourceInvalidException -> ResponseEntity(Response(null, e.message), HttpStatus.BAD_REQUEST)
            is RequestInvalidException -> ResponseEntity(Response(null, e.message), HttpStatus.BAD_REQUEST)
            else -> ResponseEntity(Response(null, e.message), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}