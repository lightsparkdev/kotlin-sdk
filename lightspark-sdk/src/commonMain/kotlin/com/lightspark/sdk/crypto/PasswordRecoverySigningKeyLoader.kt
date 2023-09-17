package com.lightspark.sdk.crypto

import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.core.crypto.RawRsaSigningKey
import com.lightspark.sdk.core.crypto.SigningKey
import com.lightspark.sdk.core.crypto.SigningKeyDecryptor
import com.lightspark.sdk.core.crypto.SigningKeyLoader
import com.lightspark.sdk.core.requester.Requester
import com.lightspark.sdk.graphql.RecoverNodeSigningKeyQuery
import saschpe.kase64.base64DecodedBytes
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

class PasswordRecoverySigningKeyLoader(
    private val nodeId: String,
    private val nodePassword: String,
    private val keyDecryptor: SigningKeyDecryptor = SigningKeyDecryptor(),
) : SigningKeyLoader {
    override suspend fun loadSigningKey(requester: Requester): SigningKey {
        val response =
            requester.makeRawRequest(
                RecoverNodeSigningKeyQuery,
                buildJsonObject { put("nodeId", nodeId) },
            )
        val keyJson =
            response["entity"]?.jsonObject?.get("encrypted_signing_private_key")?.jsonObject
                ?: throw LightsparkException("Could not recover signing key", LightsparkErrorCode.SIGNING_FAILED)
        try {
            val unencryptedKey =
                keyDecryptor.decryptKey(
                    keyJson["cipher"]!!.jsonPrimitive.content,
                    nodePassword,
                    keyJson["encrypted_value"]!!.jsonPrimitive.content,
                )
            return if (unencryptedKey[0] == 48.toByte()) {
                RawRsaSigningKey(unencryptedKey)
            } else {
                RawRsaSigningKey(unencryptedKey.decodeToString().base64DecodedBytes)
            }
        } catch (e: Exception) {
            throw LightsparkException("Could not recover signing key", LightsparkErrorCode.SIGNING_FAILED, e)
        }
    }
}
