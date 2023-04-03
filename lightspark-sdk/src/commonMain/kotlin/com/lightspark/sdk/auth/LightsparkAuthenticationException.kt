package com.lightspark.sdk.auth

import com.lightspark.sdk.LightsparkErrorCode
import com.lightspark.sdk.LightsparkException

class LightsparkAuthenticationException :
    LightsparkException(
        "Invalid or missing authentication. Please make sure you've set an account token or AuthProvider",
        LightsparkErrorCode.NO_CREDENTIALS,
    )
