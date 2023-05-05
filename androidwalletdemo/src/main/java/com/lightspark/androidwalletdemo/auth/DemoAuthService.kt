package com.lightspark.androidwalletdemo.auth

import retrofit2.http.GET
import retrofit2.http.Query

data class JwtResult(
    val token: String,
    val accountId: String,
)

interface DemoAuthService {
    @GET("getJwt")
    suspend fun getJwt(@Query("userId") userName: String, @Query("password") password: String): JwtResult
}
