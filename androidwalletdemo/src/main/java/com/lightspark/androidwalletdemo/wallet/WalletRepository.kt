package com.lightspark.androidwalletdemo.wallet

import android.util.Base64
import com.lightspark.androidwalletdemo.auth.CredentialsStore
import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.core.asLce
import com.lightspark.sdk.core.auth.AuthProvider
import com.lightspark.sdk.core.crypto.androidKeystoreContainsPrivateKeyForAlias
import com.lightspark.sdk.core.crypto.generateSigningKeyPair
import com.lightspark.sdk.core.crypto.generateSigningKeyPairInAndroidKeyStore
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.core.wrapWithLceFlow
import com.lightspark.sdk.wallet.LightsparkCoroutinesWalletClient
import com.lightspark.sdk.wallet.auth.jwt.JwtStorage
import com.lightspark.sdk.wallet.model.KeyType
import com.lightspark.sdk.wallet.model.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val LIGHTSPARK_SIGNING_KEY_ALIAS = "LightsparkSigningKey"

class WalletRepository @Inject constructor(
    private val jwtStorage: JwtStorage,
    private val credentialsStore: CredentialsStore,
    private val walletClient: LightsparkCoroutinesWalletClient,
) {
    fun loginWithJwt(accountId: String, jwt: String) = wrapWithLceFlow {
        credentialsStore.setAccountData(accountId, jwt)
        walletClient.loginWithJWT(accountId, jwt, jwtStorage)
    }

    fun setServerEnvironment(environment: ServerEnvironment, invalidateAuth: Boolean) {
        walletClient.setServerEnvironment(environment, invalidateAuth)
    }

    fun setAuthProvider(authProvider: AuthProvider) {
        walletClient.setAuthProvider(authProvider)
    }

    fun getWalletDashboard() =
        wrapWithLceFlow { walletClient.getWalletDashboard() }.flowOn(Dispatchers.IO)

    suspend fun deployWallet() =
        walletClient.deployWalletAndAwaitDeployed().asLce().flowOn(Dispatchers.IO)

    suspend fun initializeWallet(): Flow<Lce<Wallet>> {
        // TODO: Add some form of key recovery here so that keys don't get stuck.
        val keyPair = generateSigningKeyPairInAndroidKeyStore(LIGHTSPARK_SIGNING_KEY_ALIAS)
        walletClient.loadWalletSigningKeyAlias(LIGHTSPARK_SIGNING_KEY_ALIAS)
        return walletClient.initializeWalletAndWaitForInitialized(
            keyType = KeyType.RSA_OAEP,
            signingPublicKey = Base64.encodeToString(keyPair.public.encoded, Base64.NO_WRAP)
        ).asLce().flowOn(Dispatchers.IO)
    }

    fun unlockWallet(signingPrivateKey: ByteArray) =
        wrapWithLceFlow { walletClient.loadWalletSigningKey(signingPrivateKey) }

    fun attemptKeyStoreUnlock(): Boolean {
        if (androidKeystoreContainsPrivateKeyForAlias(LIGHTSPARK_SIGNING_KEY_ALIAS)) {
            walletClient.loadWalletSigningKeyAlias(LIGHTSPARK_SIGNING_KEY_ALIAS)
            return true
        }
        return false
    }

    val isWalletUnlocked = walletClient.observeWalletUnlocked()
}
