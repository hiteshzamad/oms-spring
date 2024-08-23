package com.ztech.order.model.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun responseSuccess(map: Map<String, Any?>? = null) = ResponseEntity(Response(map, "Success"), HttpStatus.OK)