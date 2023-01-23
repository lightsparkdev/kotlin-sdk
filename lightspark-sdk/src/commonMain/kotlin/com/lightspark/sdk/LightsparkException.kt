package com.lightspark.sdk

/**
 * A generic exception thrown by the Lightspark SDK.
 */
open class LightsparkException(override val message: String, val errorCode: LightsparkErrorCode) :
    Exception(message)

enum class LightsparkErrorCode {
    UNKNOWN, MISSING_NODE_KEY, MISSING_WALLET_ID, UNKNOWN_SERVER_ERROR, CONNECTION_FAILED, UNSUPPORTED_CIPHER_VERSION, MISSING_CLIENT_INIT_PARAMETER, WALLET_LOCKED
}