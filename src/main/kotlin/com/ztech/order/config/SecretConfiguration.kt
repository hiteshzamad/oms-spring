package com.ztech.order.config

import org.springframework.context.annotation.Configuration

@Configuration
class SecretConfiguration {

    fun getPasswordCryptoKey(): String = "1234567890qwertyuioppoiuytrewq12"
    fun getJwtTokenKey(): String = "8Zz5tw0Ionm3XPZZfN0NOml3z9FMfmpgXwovR9fp6ryDIoGRM8EPHAB6iHsc0fb1"

}