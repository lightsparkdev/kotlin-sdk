package com.lightspark.androiddemo.di

import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.LightsparkWalletClient
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
    fun provideFullClient(): LightsparkClient = LightsparkClient.Builder().build()

    @Provides
    @Singleton
    fun provideWalletClient(fullClient: LightsparkClient): LightsparkWalletClient =
        LightsparkWalletClient.Builder()
            .fullLightsparkClient(fullClient)
            .build()
}
