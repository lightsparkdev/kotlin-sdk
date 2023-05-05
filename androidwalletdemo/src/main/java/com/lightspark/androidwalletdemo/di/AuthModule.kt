package com.lightspark.androidwalletdemo.di

import com.lightspark.androidwalletdemo.auth.DemoAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun provideDemoAuthRetrofit(
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://us-central1-jwt-minter.cloudfunctions.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideDemoAuthService(retrofit: Retrofit): DemoAuthService = retrofit.create(DemoAuthService::class.java)
}
