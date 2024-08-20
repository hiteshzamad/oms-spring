package com.ztech.order.core

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.ztech.order.core.ControllerResponse as ResponseDto

fun responseEntity(status: Status, map: Map<String, Any?>? = null, message: String? = null): ResponseEntity<ResponseDto> {
    val statusCode: HttpStatus = when (status) {
        Status.SUCCESS -> HttpStatus.OK
        Status.CONFLICT -> HttpStatus.CONFLICT
        Status.ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
        Status.NOT_FOUND -> HttpStatus.NOT_FOUND
        Status.INVALID_INPUT -> HttpStatus.BAD_REQUEST
    }
    return ResponseEntity(
        responseBody(map, statusCode, message), statusCode
    )

}

fun responseBody(map: Map<String, Any?>?, status: HttpStatus, message: String?) = ResponseDto(
    map, message ?: when (status) {
        HttpStatus.OK -> "Success"
        HttpStatus.CONFLICT -> "Conflict"
        HttpStatus.INTERNAL_SERVER_ERROR -> "Internal Server Error"
        HttpStatus.NOT_FOUND -> "Resource Not Found"
        HttpStatus.BAD_REQUEST -> "Invalid Input"
        HttpStatus.METHOD_NOT_ALLOWED -> "Method Not Allowed"
        else -> null
    }
)