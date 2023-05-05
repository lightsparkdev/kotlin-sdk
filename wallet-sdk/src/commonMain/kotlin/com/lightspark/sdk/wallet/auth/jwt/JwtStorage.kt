package com.lightspark.sdk.wallet.auth.jwt

interface JwtStorage {
    fun getCurrent(): JwtTokenInfo?
    fun replace(state: JwtTokenInfo?)
}
