package com.lightspark.androidwalletdemo.di

import com.lightspark.androidwalletdemo.auth.CredentialsStore
import com.lightspark.androidwalletdemo.wallet.PaymentRepository
import com.lightspark.androidwalletdemo.wallet.WalletRepository
import com.lightspark.sdk.wallet.LightsparkCoroutinesWalletClient
import com.lightspark.sdk.wallet.auth.jwt.JwtStorage
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
        jwtStorage: JwtStorage,
        credentialsStore: CredentialsStore,
        walletClient: LightsparkCoroutinesWalletClient,
    ): WalletRepository =
        WalletRepository(jwtStorage, credentialsStore, walletClient)

    @Provides
    @Singleton
    fun providePaymentRepository(walletClient: LightsparkCoroutinesWalletClient): PaymentRepository =
        PaymentRepository(walletClient)
}
