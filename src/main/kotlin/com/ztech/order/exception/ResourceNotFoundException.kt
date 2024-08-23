package com.ztech.order.exception

class ResourceNotFoundException(message: String = "Resource Not Found") : ServiceException(message)