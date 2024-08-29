package com.ztech.order.config

import com.ztech.order.exception.*
import com.ztech.order.model.response.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionConfiguration : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        e: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return ResponseEntity(
            Response(mapOf("errors" to e.bindingResult.allErrors.map { it.defaultMessage }), "Invalid Request"),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler
    fun handleException(e: Exception): ResponseEntity<Response> {
        println("Exception: ${e.message} $e")
        return when (e) {
            is AuthorizationDeniedException -> ResponseEntity(Response(null, e.message), HttpStatus.UNAUTHORIZED)
            is ResourceNotFoundException -> ResponseEntity(Response(null, e.message), HttpStatus.NOT_FOUND)
            is ResourceConflictException -> ResponseEntity(Response(null, e.message), HttpStatus.CONFLICT)
            is ResourceInvalidException -> ResponseEntity(Response(null, e.message), HttpStatus.BAD_REQUEST)
            is RequestInvalidException -> ResponseEntity(Response(null, e.message), HttpStatus.BAD_REQUEST)
            is AuthenticationException -> ResponseEntity(Response(null, e.message), HttpStatus.FORBIDDEN)
            is MethodArgumentNotValidException ->
                ResponseEntity(
                    Response(null, e.bindingResult.allErrors.map { it.defaultMessage }.joinToString()),
                    HttpStatus.BAD_REQUEST
                )
            else -> ResponseEntity(Response(null, e.message), HttpStatus.INTERNAL_SERVER_ERROR).apply {
                println("Exception: ${e.message} $e")
            }
        }
    }
}