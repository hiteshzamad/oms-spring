package com.ztech.order.core

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

data class ControllerResponse(
    @JsonInclude(Include.NON_NULL)
    val data: Map<String, Any?>?,
    @JsonInclude(Include.NON_NULL)
    val message: String?
)