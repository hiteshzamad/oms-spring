package com.ztech.order.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

data class Response(
    @JsonInclude(Include.NON_NULL)
    val data: Map<String, Any?>?,
    @JsonInclude(Include.NON_NULL)
    val message: String?
)