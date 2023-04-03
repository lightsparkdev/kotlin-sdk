package com.lightspark.androiddemo.di

import android.content.Context
import com.lightspark.androiddemo.auth.CredentialsStore
import com.lightspark.androiddemo.settings.DefaultPrefsStore
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
