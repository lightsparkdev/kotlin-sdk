package com.lightspark.sdk.core.crypto

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import saschpe.kase64.base64DecodedBytes

private const val HEADER_SIZE = 8
private const val KEY_LENGTH = 32

class SigningKeyDecryptor {
    fun decryptKey(
        cipherVersion: String,
        nodePassword: String,
        encryptedKeyBase64: String,
    ): ByteArray {
        var decodedEncryptedKey = encryptedKeyBase64.base64DecodedBytes
        var header = Header(0, 0)
        if (cipherVersion == "AES_256_CBC_PBKDF2_5000_SHA256") {
            decodedEncryptedKey =
                decodedEncryptedKey.copyOfRange(HEADER_SIZE, decodedEncryptedKey.size)
        } else {
            header = Json.decodeFromString(cipherVersion)
        }
        if (header.lsv == 2) {
            header = header.copy(v = 3)
        }

        if (header.v !in 0..4) {
            throw UnsupportedCipherVersionException(header.v)
        }

        if (header.v == 3) {
            return decryptKeyV3(nodePassword, header, decodedEncryptedKey)
        }
        val saltLength = if (header.v < 4) 8 else 16
        val ivLength = if (header.v < 4) 16 else 12

        val salt = decodedEncryptedKey.copyOfRange(0, saltLength)
        val cipherText = decodedEncryptedKey.copyOfRange(saltLength, decodedEncryptedKey.size)
        val keyPlusIV = com.lightspark.sdk.core.crypto.pbkdf2(nodePassword, salt, header.i, KEY_LENGTH + ivLength)
        val key = keyPlusIV.copyOfRange(0, KEY_LENGTH)
        val iv = keyPlusIV.copyOfRange(KEY_LENGTH, KEY_LENGTH + ivLength)

        return if (header.v < 2) {
            com.lightspark.sdk.core.crypto.decryptKeyCBC(key, iv, cipherText)
        } else {
            com.lightspark.sdk.core.crypto.decryptKeyGCM(key, iv, cipherText)
        }
    }

    private fun decryptKeyV3(
        nodePassword: String,
        header: Header,
        decodedEncryptedKey: ByteArray,
    ): ByteArray {
        val salt =
            decodedEncryptedKey.copyOfRange(decodedEncryptedKey.size - 16, decodedEncryptedKey.size)
        val nonce = decodedEncryptedKey.copyOfRange(0, 12)
        val cipherText =
            decodedEncryptedKey.copyOfRange(12, decodedEncryptedKey.size - 8)
        val key = com.lightspark.sdk.core.crypto.pbkdf2(nodePassword, salt, header.i, KEY_LENGTH)
        return com.lightspark.sdk.core.crypto.decryptKeyGCM(key, nonce, cipherText)
    }
}

@kotlinx.serialization.Serializable
private data class Header(val i: Int, val v: Int, val lsv: Int? = null)
