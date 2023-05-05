package com.lightspark.sdk.core.crypto

internal expect fun decryptKeyCBC(key: ByteArray, iv: ByteArray, cipherText: ByteArray): ByteArray
internal expect fun decryptKeyGCM(key: ByteArray, iv: ByteArray, cipherText: ByteArray): ByteArray
