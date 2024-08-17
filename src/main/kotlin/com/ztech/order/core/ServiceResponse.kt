package com.ztech.order.core

class ServiceResponse<T>(val status: Status, val data: T? = null)

enum class Status {
    SUCCESS, ERROR, CONFLICT, NOT_FOUND, INVALID_INPUT
}