package com.lightspark.sdk

import com.apollographql.apollo3.ApolloClient
import com.lightspark.api.PayInvoiceMutation
import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.api.type.CurrencyAmountInput
import com.lightspark.conf.BuildKonfig
import com.lightspark.sdk.crypto.NodeKeyCache
import com.lightspark.sdk.model.CurrencyAmount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LightsparkWalletClient private constructor(
    private val fullClient: LightsparkClient,
    private val nodeKeyCache: NodeKeyCache,
    private val apolloClient: ApolloClient
) {
    private var walletId: String? = null

    /**
     * Get the dashboard overview for the active lightning wallet. Includes balance info and
     * the most recent transactions.
     *
     * @param numTransactions The max number of recent transactions to fetch. Defaults to 20.
     * @param bitcoinNetwork The bitcoin network to use for the dashboard data. Defaults to the network set in the
     *      gradle project properties
     * @return The dashboard overview for the node, including balance and recent transactions.
     */
    suspend fun getWalletDashboard(
        numTransactions: Int = 20,
        bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK),
    ) = fullClient.getWalletDashboard(
        requireWalletId(),
        numTransactions,
        bitcoinNetwork
    )

    /**
     * Unlocks the wallet for use with sensitive SDK operations. Also sets the active wallet to the specified
     * walletId. This function must be called before calling any other functions that operate on the wallet.
     *
     * @param password The password for the wallet.
     * @return True if the wallet was unlocked successfully, false otherwise.
     */
    suspend fun unlockWallet(walletId: String, password: String): Boolean {
        this.walletId = walletId
        return fullClient.recoverNodeSigningKey(requireWalletId(), password)
    }

    /**
     * Locks the active wallet if needed to prevent payment until the password is entered again and passed to
     * [unlockWallet].
     */
    fun lockWallet() = nodeKeyCache.remove(requireWalletId())

    /**
     * Creates a lightning invoice for a payment to this wallet.
     *
     * @param amount The amount of the invoice in a specified currency unit.
     * @param memo Optional memo to include in the invoice.
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
        nodeKeyCache.observeCachedNodeIds().map { it.contains(requireWalletId()) }

    /**
     * @return True if the wallet is unlocked or false if it is locked.
     */
    fun isWalletUnlocked() = nodeKeyCache.contains(requireWalletId())

    private fun requireWalletId() =
        walletId ?: throw LightsparkException(
            "Missing wallet ID",
            LightsparkErrorCode.MISSING_WALLET_ID
        )

    class Builder {
        private var serverUrl: String = BuildKonfig.LIGHTSPARK_ENDPOINT
        private var tokenId = BuildKonfig.LIGHTSPARK_TOKEN_ID
        private var token = BuildKonfig.LIGHTSPARK_TOKEN
        private var walletId: String? = null
        private var fullClient: LightsparkClient? = null

        fun serverUrl(serverUrl: String) = apply { this.serverUrl = serverUrl }
        fun tokenId(tokenId: String) = apply { this.tokenId = tokenId }
        fun token(token: String) = apply { this.token = token }
        fun walletId(walletId: String) = apply { this.walletId = walletId }
        fun fullLightsparkClient(fullClient: LightsparkClient) =
            apply { this.fullClient = fullClient }


        fun build(): LightsparkWalletClient {
            if (fullClient == null && (serverUrl.isBlank() || tokenId.isBlank() || token.isBlank())) {
                throw LightsparkException(
                    "Missing server URL, token ID, or token",
                    LightsparkErrorCode.MISSING_CLIENT_INIT_PARAMETER
                )
            }

            val nodeKeyCache = NodeKeyCache()
            val delegateFullClient = fullClient ?: LightsparkClient(
                tokenId,
                token,
                serverUrl,
                nodeKeyCache = nodeKeyCache
            )

            return LightsparkWalletClient(
                delegateFullClient,
                nodeKeyCache,
                delegateFullClient.apolloClient
            ).apply { walletId = this@Builder.walletId }
        }
    }
}