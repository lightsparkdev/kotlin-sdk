@file:JvmName("SigningJvm")

package com.lightspark.sdk.core.crypto

internal actual fun signUsingAlias(payload: ByteArray, keyAlias: String): ByteArray {
    throw IllegalStateException("Not yet implemented for jvm")
}
