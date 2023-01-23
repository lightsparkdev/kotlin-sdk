package com.lightspark.sdk.crypto

internal expect fun signPayload(payload: ByteArray, key: ByteArray): ByteArray
