package com.ztech.order.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class Response(
    @JsonInclude(Include.NON_NULL)
    val data: Map<String, Any?>?,
    @JsonInclude(Include.NON_NULL)
    val message: String?
)

fun responseSuccess(map: Map<String, Any?>? = null) =
    ResponseEntity(Response(map, "Success"), HttpStatus.OK)
