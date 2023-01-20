package com.lightspark.sdk

open class LightsparkException(override val message: String, val errorCode: LightsparkErrorCode) :
    Exception(message)

enum class LightsparkErrorCode {
    UNKNOWN, MISSING_NODE_KEY, UNKNOWN_SERVER_ERROR, CONNECTION_FAILED, UNSUPPORTED_CIPHER_VERSION
}