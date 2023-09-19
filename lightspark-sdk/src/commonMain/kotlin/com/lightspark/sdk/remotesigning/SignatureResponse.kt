package com.lightspark.sdk.remotesigning

import kotlinx.serialization.Serializable

@Serializable
data class SignatureResponse(
    val id: String,
    val signature: String,
)
