package com.lightspark.sdk.wallet.auth.jwt

class InMemoryJwtStorage : JwtStorage {
    private var state: JwtTokenInfo? = null

    override fun getCurrent(): JwtTokenInfo? = state

    override fun replace(state: JwtTokenInfo?) {
        this.state = state
    }
}
