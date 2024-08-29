package com.ztech.order.util

import java.util.*
import java.util.Base64.Decoder
import java.util.Base64.Encoder
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class CryptoAESUtil(encryptionKey: String) {

    private val encoder: Encoder = Base64.getEncoder()
    private val decoder: Decoder = Base64.getDecoder()
    private val encryptCipher: Cipher = Cipher.getInstance("AES")
    private val decryptCipher: Cipher = Cipher.getInstance("AES")

    init {
        val keySpec = SecretKeySpec(encryptionKey.toByteArray(), "AES")
        encryptCipher.init(Cipher.ENCRYPT_MODE, keySpec)
        decryptCipher.init(Cipher.DECRYPT_MODE, keySpec)
    }

    fun encrypt(plaintext: String): String {
        return encoder.encodeToString(encryptCipher.doFinal(plaintext.toByteArray()))
    }

    fun decrypt(ciphertext: String): String {
        return String(decryptCipher.doFinal(decoder.decode(ciphertext)))
    }

    fun encrypt(plaintext: ByteArray): ByteArray {
        return encoder.encode(encryptCipher.doFinal(plaintext))
    }

    fun decrypt(ciphertext: ByteArray): ByteArray {
        return decryptCipher.doFinal(decoder.decode(ciphertext))
    }
}
