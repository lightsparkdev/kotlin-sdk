package com.lightspark.androidwalletdemo.di

import android.content.Context
import com.lightspark.androidwalletdemo.settings.DefaultPrefsStore
import com.lightspark.sdk.wallet.ClientConfig
import com.lightspark.sdk.wallet.LightsparkCoroutinesWalletClient
import com.lightspark.sdk.wallet.auth.jwt.JwtStorage
import com.lightspark.sdk.wallet.auth.jwt.SharedPrefsJwtStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LightsparkSdkModule {
    @Provides
    @Singleton
    fun provideClient(prefsStore: DefaultPrefsStore): LightsparkCoroutinesWalletClient =
        LightsparkCoroutinesWalletClient(ClientConfig(serverUrl = prefsStore.getPrefsSync().environment.graphQLUrl))

    @Provides
    @Singleton
    fun provideJwtStorage(@ApplicationContext context: Context): JwtStorage = SharedPrefsJwtStorage(context)
}
