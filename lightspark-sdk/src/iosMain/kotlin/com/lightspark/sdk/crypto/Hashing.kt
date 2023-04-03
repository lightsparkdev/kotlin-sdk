package com.lightspark.sdk.crypto

actual fun pbkdf2(
    password: String,
    salt: ByteArray,
    iterations: Int,
    keyLengthBytes: Int,
): ByteArray {
    TODO
}
