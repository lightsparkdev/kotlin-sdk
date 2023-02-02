package com.lightspark.androiddemo.di

import com.lightspark.androiddemo.accountdashboard.AccountDashboardRepository
import com.lightspark.androiddemo.settings.DefaultPrefsStore
import com.lightspark.androiddemo.wallet.PaymentRepository
import com.lightspark.androiddemo.wallet.WalletRepository
import com.lightspark.sdk.LightsparkClient
import com.lightspark.sdk.LightsparkWalletClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideWalletRepository(
        prefsStore: DefaultPrefsStore,
        walletClient: LightsparkWalletClient
    ): WalletRepository =
        WalletRepository(prefsStore, walletClient)

    @Provides
    @Singleton
    fun provideDashboardRepository(lightsparkClient: LightsparkClient): AccountDashboardRepository =
        AccountDashboardRepository(lightsparkClient)

    @Provides
    @Singleton
    fun providePaymentRepository(walletClient: LightsparkWalletClient): PaymentRepository =
        PaymentRepository(walletClient)
}