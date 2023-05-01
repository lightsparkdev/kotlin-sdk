package com.lightspark.androidwalletdemo.di

import android.content.Context
import com.lightspark.androidwalletdemo.auth.CredentialsStore
import com.lightspark.androidwalletdemo.settings.DefaultPrefsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    @Singleton
    fun providePrefsStore(@ApplicationContext appContext: Context): DefaultPrefsStore =
        DefaultPrefsStore(appContext)

    @Provides
    @Singleton
    fun provideCredentialsStore(@ApplicationContext appContext: Context): CredentialsStore =
        CredentialsStore(appContext)
}
