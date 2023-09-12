package com.lightspark.sdk.crypto

import com.lightspark.sdk.crypto.internal.toByteArray

object Secp256k1 {
    fun signEcdsa(message: ByteArray, privateKey: ByteArray): ByteArray {
        return com.lightspark.sdk.crypto.internal.signEcdsa(
            message.toUByteArray().toList(), privateKey.toUByteArray().toList(),
        ).toByteArray()
    }

    fun verifyEcdsa(message: ByteArray, signature: ByteArray, publicKey: ByteArray): Boolean {
        return com.lightspark.sdk.crypto.internal.verifyEcdsa(
            message.toUByteArray().toList(), signature.toUByteArray().toList(),
            publicKey.toUByteArray().toList(),
        )
    }

    fun encryptEcies(message: ByteArray, publicKey: ByteArray): ByteArray {
        return com.lightspark.sdk.crypto.internal.encryptEcies(
            message.toUByteArray().toList(), publicKey.toUByteArray().toList(),
        ).toByteArray()
    }

    fun decryptEcies(message: ByteArray, privateKey: ByteArray): ByteArray {
        return com.lightspark.sdk.crypto.internal.decryptEcies(
            message.toUByteArray().toList(), privateKey.toUByteArray().toList(),
        ).toByteArray()
    }
}
