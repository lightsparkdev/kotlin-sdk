package com.lightspark.sdk.crypto

internal actual fun signPayload(payload: ByteArray, key: ByteArray): ByteArray {
    // Sign the payload with the node key using SHA256withECDSA and PSS padding
    val signature = java.security.Signature.getInstance("SHA256withECDSA")
    signature.setParameter(
        java.security.spec.PSSParameterSpec(
            "SHA-256",
            "MGF1",
            java.security.spec.MGF1ParameterSpec("SHA-256"),
            32,
            1
        )
    )
    signature.initSign(
        java.security.KeyFactory.getInstance("EC")
            .generatePrivate(java.security.spec.PKCS8EncodedKeySpec(key))
    )
    signature.update(payload)
    return signature.sign()
}
