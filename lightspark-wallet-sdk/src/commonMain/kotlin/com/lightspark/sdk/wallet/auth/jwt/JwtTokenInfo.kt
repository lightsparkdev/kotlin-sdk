package com.lightspark.sdk.wallet.auth.jwt

import kotlinx.datetime.Instant

data class JwtTokenInfo(
    val accessToken: String,
    val validUntil: Instant,
)
