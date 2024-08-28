package com.ztech.order.component

import org.springframework.stereotype.Component

@Component
class SecretConfiguration {

    fun getPasswordCryptoKey(): String = "1234567890qwertyuioppoiuytrewq12"
}