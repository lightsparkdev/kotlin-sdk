package com.lightspark.sdk.core.crypto

expect fun pbkdf2(
    password: String,
    salt: ByteArray,
    iterations: Int,
    keyLengthBytes: Int,
): ByteArray
