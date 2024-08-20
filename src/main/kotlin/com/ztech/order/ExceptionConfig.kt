package com.ztech.order

import com.ztech.order.core.responseBody
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionConfig : ResponseEntityExceptionHandler() {

    override fun handleExceptionInternal(
        ex: Exception, body: Any?, headers: HttpHeaders, statusCode: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any>? {
        return super.handleExceptionInternal(
            ex, responseBody(null, statusCode as HttpStatus, null), headers, statusCode, request
        )
    }
}