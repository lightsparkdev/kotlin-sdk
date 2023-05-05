package com.lightspark.sdk.core.crypto

actual fun pbkdf2(
    password: String,
    salt: ByteArray,
    iterations: Int,
    keyLengthBytes: Int,
): ByteArray {
    TODO
}
