package com.ztech.order.core

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.ztech.order.core.ControllerResponse as ResponseDto

fun responseEntity(status: Status, map: Map<String, Any?> = mapOf()): ResponseEntity<ResponseDto> = ResponseEntity(
    ResponseDto(map), when (status) {
        Status.SUCCESS -> HttpStatus.OK
        Status.CONFLICT -> HttpStatus.CONFLICT
        Status.ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
        Status.NOT_FOUND -> HttpStatus.NOT_FOUND
        Status.INVALID_INPUT -> HttpStatus.BAD_REQUEST
    }
)
