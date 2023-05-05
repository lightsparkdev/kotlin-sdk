package com.lightspark.androidwalletdemo.auth

import com.lightspark.sdk.core.wrapWithLceFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(private val demoAuthService: DemoAuthService) {
    fun getJwt(userName: String, password: String) = wrapWithLceFlow { demoAuthService.getJwt(userName, password) }
}
