package com.lightspark.sdk.crypto

import java.security.Signature

internal actual fun signPayload(payload: ByteArray, key: ByteArray): ByteArray {
//    Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
//    Security.addProvider(BouncyCastleProvider())
//    val provider = Conscrypt.newProvider()
//    Security.addProvider(provider)
    val signature = Signature.getInstance("SHA256withRSA/PSS")
//    signature.setParameter(
//        java.security.spec.PSSParameterSpec(
//            "SHA-256",
//            "MGF1",
//            java.security.spec.MGF1ParameterSpec.SHA256,
//            32,
//            1
//        )
//    )
    signature.initSign(
        java.security.KeyFactory.getInstance("RSA")
            .generatePrivate(java.security.spec.PKCS8EncodedKeySpec(key))
    )
    signature.update(payload)
    return signature.sign()
}
