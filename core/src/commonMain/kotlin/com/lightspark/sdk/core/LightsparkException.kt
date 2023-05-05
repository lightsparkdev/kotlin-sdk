package com.lightspark.sdk.core

/**
 * A generic exception thrown by the Lightspark SDK.
 */
open class LightsparkException(
    override val message: String,
    val errorCode: String,
    override val cause: Throwable? = null,
) : Exception(message, cause) {
    constructor(message: String, errorCode: LightsparkErrorCode, cause: Throwable? = null) : this(
        message,
        errorCode.name,
        cause,
    )
}

enum class LightsparkErrorCode {
    UNKNOWN,
    NO_CREDENTIALS,
    JWT_AUTH_ERROR,
    MISSING_NODE_KEY,
    MISSING_WALLET_ID,
    UNKNOWN_SERVER_ERROR,
    CONNECTION_FAILED,
    REQUEST_FAILED,
    UNSUPPORTED_CIPHER_VERSION,
    MISSING_CLIENT_INIT_PARAMETER,
    SIGNING_FAILED,
    WALLET_LOCKED,
    WALLET_DEPLOY_FAILED,
    WALLET_INIT_FAILED,
    WALLET_TERMINATE_FAILED,
    INVALID_QUERY,
    PAYMENT_ERROR,
}
