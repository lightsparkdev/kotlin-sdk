package com.lightspark.sdk

import com.apollographql.apollo3.ApolloClient
import com.lightspark.api.PayInvoiceMutation
import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.api.type.CurrencyAmountInput
import com.lightspark.conf.BuildKonfig
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import com.lightspark.sdk.auth.AuthProvider
import com.lightspark.sdk.crypto.NodeKeyCache
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.WalletDashboardData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Main entry point for the Lightspark Wallet SDK.
 *
 * Should be constructed using the Builder class.
 *
 * ```kotlin
 * // Initialize the client with account token info:
 * val lightsparkClient = LightsparkWalletClient.Builder().apply {
 *     tokenId = "your-token-id"
 *     token = "your-secret-token"
 * }.build()
 *
 * // An example API call fetching the dashboard info for the active account:
 * val dashboard = lightsparkWalletClient.getWalletDashboard()
 * ```
 *
 * Note: This client object keeps a local cache in-memory, so a single instance should be reused
 * throughout the lifetime of your app.
 */
class LightsparkWalletClient private constructor(
    private val fullClient: LightsparkClient,
    private val nodeKeyCache: NodeKeyCache,
    private val apolloClient: ApolloClient
) {
    var activeWalletId: String? = null
        private set

    /**
     * Creates a new wallet for the user. This will create a new node on the server.
     *
     * @param password The password used to encrypt the wallet's keys.
     * @param bitcoinNetwork The bitcoin network to use for the dashboard data. Defaults to the network set in the
     *      gradle project properties
     */
    suspend fun createNewWallet(
        password: String,
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK),
    ) {
        TODO("Not yet implemented")
    }

    /**
     * Get the dashboard overview for the active lightning wallet. Includes balance info and
     * the most recent transactions.
     *
     * @param numTransactions The max number of recent transactions to fetch. Defaults to 20.
     * @param bitcoinNetwork The bitcoin network to use for the dashboard data. Defaults to the network set in the
     *      gradle project properties
     * @return The dashboard overview for the node, including balance and recent transactions.
     * @throws LightsparkException If the wallet ID is not set yet.
     */
    suspend fun getWalletDashboard(
        numTransactions: Int = 20,
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK),
    ): WalletDashboardData? {
        fullClient.requireValidAuth()
        return fullClient.getSingleNodeDashboard(
            requireWalletId(),
            numTransactions,
            bitcoinNetwork
        )
    }

    /**
     * Unlocks the wallet for use with sensitive SDK operations. Also sets the active wallet to the specified
     * walletId. This function must be called before calling any other functions that operate on the wallet.
     *
     * @param walletId The ID of the wallet to unlock.
     * @param password The password for the wallet.
     * @return True if the wallet was unlocked successfully, false otherwise.
     */
    suspend fun unlockWallet(walletId: String, password: String): Boolean {
        this.activeWalletId = walletId
        return fullClient.recoverNodeSigningKey(requireWalletId(), password)
    }

    /**
     * Unlocks the active wallet for use with sensitive SDK operations. This function or [unlockWallet] must be called
     * before calling sensitive operations like [payInvoice].
     *
     * @param password The password for the wallet.
     * @return True if the wallet was unlocked successfully, false otherwise.
     * @throws LightsparkException If the wallet ID is not set yet.
     */
    suspend fun unlockActiveWallet(password: String) = unlockWallet(requireWalletId(), password)

    /**
     * Sets the active wallet, but does not attempt to unlock it. If the wallet is not unlocked, sensitive operations
     * like [payInvoice] will fail.
     */
    fun setActiveWalletWithoutUnlocking(walletId: String?) {
        this.activeWalletId = walletId
    }

    /**
     * Locks the active wallet if needed to prevent payment until the password is entered again and passed to
     * [unlockWallet].
     *
     * @throws LightsparkException If the wallet ID is not set yet.
     */
    fun lockWallet() = nodeKeyCache.remove(requireWalletId())

    /**
     * Creates a lightning invoice for a payment to this wallet.
     *
     * @param amount The amount of the invoice in a specified currency unit.
     * @param memo Optional memo to include in the invoice.
     * @throws LightsparkException If the wallet ID is not set yet.
     */
    suspend fun createInvoice(
        amount: CurrencyAmount,
        memo: String? = null,
    ) = fullClient.createInvoice(requireWalletId(), amount, memo)

    /**
     * Pay a lightning invoice from this wallet.
     *
     * Note: This call will fail if the wallet is not unlocked yet via the [unlockWallet] function. You must
     * successfully unlock the node with its password before calling this function.
     *
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @param amount The amount to pay in a specified currency unit. Defaults to the full amount of the invoice.
     * @param maxFees The maximum fees to pay in a specified currency unit.
     * @return The payment details.
     * @throws LightsparkException If the wallet is not unlocked yet.
     */
    suspend fun payInvoice(
        encodedInvoice: String,
        timeoutSecs: Int = 60,
        amount: CurrencyAmountInput? = null,
        maxFees: CurrencyAmountInput? = null,
    ): PayInvoiceMutation.Payment {
        if (!nodeKeyCache.contains(requireWalletId())) {
            throw LightsparkException(
                "Wallet is locked. Call unlockWallet before calling payInvoice.",
                LightsparkErrorCode.WALLET_LOCKED
            )
        }
        return fullClient.payInvoice(
            requireWalletId(),
            encodedInvoice,
            timeoutSecs,
            amount,
            maxFees
        )
    }

    /**
     * Decode a lightning invoice to get its details included payment amount, destination, etc.
     *
     * @param encodedInvoice An encoded string representation of the invoice to decode.
     * @return The decoded invoice details.
     */
    suspend fun decodeInvoice(encodedInvoice: String) = fullClient.decodeInvoice(encodedInvoice)

    /**
     * Get the fee estimate for a payment.
     *
     * @param bitcoinNetwork The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle
     *      project properties.
     * @return The fee estimate including a fast and minimum fee as [CurrencyAmount]s
     */
    suspend fun getFeeEstimate(
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)
    ) = fullClient.getFeeEstimate(bitcoinNetwork)

    /**
     * @return A [Flow] that emits true if the wallet is unlocked or false if it is locked.
     */
    fun observeWalletUnlocked() =
        nodeKeyCache.observeCachedNodeIds().map { unlockedIds ->
            activeWalletId?.let { unlockedIds.contains(it) } ?: false
        }

    /**
     * @return True if the wallet is unlocked or false if it is locked.
     */
    fun isWalletUnlocked() = activeWalletId?.let { nodeKeyCache.contains(it) } ?: false

    private fun requireWalletId() =
        activeWalletId ?: throw LightsparkException(
            "Missing wallet ID",
            LightsparkErrorCode.MISSING_WALLET_ID
        )

    class Builder {
        private var serverUrl: String = BuildKonfig.LIGHTSPARK_ENDPOINT
        private var tokenId = BuildKonfig.LIGHTSPARK_TOKEN_ID
        private var token = BuildKonfig.LIGHTSPARK_TOKEN
        private var authProvider: AuthProvider? = null
        private var walletId: String? = null
        private var fullClient: LightsparkClient? = null

        fun serverUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }
        fun tokenId(tokenId: String) = apply { this.tokenId = tokenId }
        fun token(token: String) = apply { this.token = token }
        fun walletId(walletId: String) = apply { this.walletId = walletId }
        fun authProvider(authProvider: AuthProvider) =
            apply { this.authProvider = authProvider }

        fun fullLightsparkClient(fullClient: LightsparkClient) =
            apply { this.fullClient = fullClient }


        fun build(): LightsparkWalletClient {
            val isTokenValid =
                authProvider != null || (tokenId.isNotBlank() && token.isNotBlank())
            if (fullClient == null && (serverUrl.isBlank() || !isTokenValid)) {
                throw LightsparkException(
                    "Missing server URL, token ID, or token",
                    LightsparkErrorCode.MISSING_CLIENT_INIT_PARAMETER
                )
            }

            val delegateFullClient = fullClient ?: LightsparkClient(
                authProvider ?: AccountApiTokenAuthProvider(tokenId, token),
                serverUrl,
                nodeKeyCache = NodeKeyCache()
            )

            return LightsparkWalletClient(
                delegateFullClient,
                delegateFullClient.nodeKeyCache,
                delegateFullClient.apolloClient
            ).apply { activeWalletId = this@Builder.walletId }
        }
    }
}