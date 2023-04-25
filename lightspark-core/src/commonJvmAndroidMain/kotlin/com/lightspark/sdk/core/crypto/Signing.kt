package com.lightspark.sdk.core.crypto

import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.Signature
import java.security.SignatureException
import java.security.spec.MGF1ParameterSpec
import java.security.spec.PSSParameterSpec

@Throws(InvalidKeyException::class, NoSuchAlgorithmException::class, SignatureException::class)
internal actual fun signPayload(payload: ByteArray, key: ByteArray): ByteArray {
    val signature = try {
        Signature.getInstance("SHA256withRSA/PSS")
    } catch (e: NoSuchAlgorithmException) {
        // Fallback to RSASSA-PSS if SHA256withRSA/PSS is not supported
        Signature.getInstance("RSASSA-PSS").apply {
            setParameter(PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1))
        }
    }

    signature.initSign(
        java.security.KeyFactory.getInstance("RSA")
            .generatePrivate(java.security.spec.PKCS8EncodedKeySpec(key)),
    )
    signature.update(payload)
    return signature.sign()
}
