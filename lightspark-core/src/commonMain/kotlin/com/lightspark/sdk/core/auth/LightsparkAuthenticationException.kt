package com.lightspark.sdk.core.auth

import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException

class LightsparkAuthenticationException :
    LightsparkException(
        "Invalid or missing authentication. Please make sure you've set an account token or AuthProvider",
        LightsparkErrorCode.NO_CREDENTIALS,
    )
