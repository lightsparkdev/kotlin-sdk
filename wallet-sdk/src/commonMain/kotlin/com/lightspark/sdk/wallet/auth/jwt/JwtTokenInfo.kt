package com.lightspark.sdk.wallet.auth.jwt

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class JwtTokenInfo(
    val accessToken: String,
    val validUntil: Instant,
)
