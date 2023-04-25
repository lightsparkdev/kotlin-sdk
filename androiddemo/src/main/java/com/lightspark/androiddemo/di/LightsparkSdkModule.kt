package com.lightspark.androiddemo.di

import com.lightspark.sdk.server.ClientConfig
import com.lightspark.sdk.server.LightsparkCoroutinesClient
import com.lightspark.sdk.server.LightsparkWalletClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LightsparkSdkModule {
    @Provides
    @Singleton
    fun provideFullClient(): LightsparkCoroutinesClient = LightsparkCoroutinesClient(ClientConfig())

    @Provides
    @Singleton
    fun provideWalletClient(fullClient: LightsparkCoroutinesClient): LightsparkWalletClient =
        LightsparkWalletClient(fullClient)
}
