package com.lightspark.sdk.crypto

import java.security.Signature

internal actual fun signPayload(payload: ByteArray, key: ByteArray): ByteArray {
    val signature = Signature.getInstance("SHA256withRSA/PSS")
    signature.initSign(
        java.security.KeyFactory.getInstance("RSA")
            .generatePrivate(java.security.spec.PKCS8EncodedKeySpec(key)),
    )
    signature.update(payload)
    return signature.sign()
}
