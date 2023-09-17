package com.lightspark.sdk.core.crypto

import com.lightspark.sdk.core.requester.Requester

interface SigningKeyLoader {
    suspend fun loadSigningKey(requester: Requester): SigningKey
}

class RawRsaSigningKeyLoader(private val privateKeyBytes: ByteArray) : SigningKeyLoader {
    override suspend fun loadSigningKey(requester: Requester): SigningKey {
        return RawRsaSigningKey(privateKeyBytes)
    }
}

class AliasedRsaSigningKeyLoader(private val keyAlias: String) : SigningKeyLoader {
    override suspend fun loadSigningKey(requester: Requester): SigningKey {
        return AliasedRsaSigningKey(keyAlias)
    }
}
