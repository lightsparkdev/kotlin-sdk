package com.lightspark.sdk.crypto

import javax.crypto.SecretKeyFactory

actual fun pbkdf2(
    password: String,
    salt: ByteArray,
    iterations: Int,
    keyLengthBytes: Int,
): ByteArray {
    val factory = SecretKeyFactory.getInstance("PBKDF2withHmacSHA256")
    val spec =
        javax.crypto.spec.PBEKeySpec(password.toCharArray(), salt, iterations, keyLengthBytes * 8)
    return factory.generateSecret(spec).encoded
}
