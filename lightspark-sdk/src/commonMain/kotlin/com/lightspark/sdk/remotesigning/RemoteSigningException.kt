package com.lightspark.sdk.remotesigning

import com.lightspark.sdk.core.LightsparkException

class RemoteSigningException(
    message: String,
    cause: Throwable? = null,
) : LightsparkException(message, "remote_signing_error", cause)
