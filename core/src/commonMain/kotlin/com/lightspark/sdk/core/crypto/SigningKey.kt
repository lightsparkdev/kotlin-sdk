package com.lightspark.sdk.core.crypto

import com.lightspark.sdk.crypto.Secp256k1

sealed interface SigningKey {
    fun sign(payload: ByteArray): ByteArray
}

class Secp256k1SigningKey(private val privateKeyBytes: ByteArray) : SigningKey {
    override fun sign(payload: ByteArray): ByteArray {
        return Secp256k1.signEcdsa(payload, privateKeyBytes)
    }
}

class RawRsaSigningKey(private val privateKeyBytes: ByteArray) : SigningKey {
    override fun sign(payload: ByteArray): ByteArray {
        return signPayload(payload, privateKeyBytes)
    }
}

class AliasedRsaSigningKey(private val keyAlias: String) : SigningKey {
    override fun sign(payload: ByteArray): ByteArray {
        return signUsingAlias(payload, keyAlias)
    }
}
