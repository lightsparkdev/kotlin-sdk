package com.lightspark.androidwalletdemo.di

import com.lightspark.androidwalletdemo.BuildConfig
import com.lightspark.androidwalletdemo.auth.DemoAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun provideDemoAuthRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.JWT_SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideDemoAuthService(retrofit: Retrofit): DemoAuthService = retrofit.create(DemoAuthService::class.java)
}
