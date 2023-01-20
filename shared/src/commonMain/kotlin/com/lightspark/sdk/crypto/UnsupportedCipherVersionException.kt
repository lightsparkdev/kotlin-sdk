package com.lightspark.sdk.crypto

import com.lightspark.sdk.LightsparkErrorCode
import com.lightspark.sdk.LightsparkException

class UnsupportedCipherVersionException(version: Int) : LightsparkException(
    "Unknown version $version",
    LightsparkErrorCode.UNSUPPORTED_CIPHER_VERSION
)