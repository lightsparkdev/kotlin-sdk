package com.lightspark.sdk.core.crypto

import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException

class UnsupportedCipherVersionException(version: Int) : LightsparkException(
    "Unknown version $version",
    LightsparkErrorCode.UNSUPPORTED_CIPHER_VERSION,
)
