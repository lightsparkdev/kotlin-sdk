package com.lightspark.sdk.core.auth

import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException

class LightsparkAuthenticationException(
    override val message: String =
        "Invalid or missing authentication. Please make sure you've set an account token or AuthProvider",
) : LightsparkException(
    message,
    LightsparkErrorCode.NO_CREDENTIALS,
)
