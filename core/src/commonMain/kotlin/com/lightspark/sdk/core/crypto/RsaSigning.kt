package com.lightspark.sdk.core.crypto

internal expect fun signPayload(payload: ByteArray, key: ByteArray): ByteArray

internal expect fun signUsingAlias(payload: ByteArray, keyAlias: String): ByteArray
