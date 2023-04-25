package com.lightspark.sdk.server

import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.core.crypto.NodeKeyCache
import com.lightspark.sdk.core.requester.Requester
import com.lightspark.sdk.server.graphql.WalletDashboard
import com.lightspark.sdk.server.model.CurrencyAmount
import com.lightspark.sdk.server.model.OutgoingPayment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Main entry point for the Lightspark Wallet SDK.
 *
 * Should be constructed using the ClientConfig constructor:
 *
 * ```kotlin
 * // Initialize the client with account token info:
 * val lightsparkClient = LightsparkWalletClient(ClientConfig(
 *     authProvider = AccountApiTokenAuthProvider(
 *         tokenId = "your-token-id"
 *         token = "your-secret-token"
 *     )
 * ))
 *
 * // An example API call fetching the dashboard info for the active account:
 * val dashboard = lightsparkWalletClient.getWalletDashboard()
 * ```
 *
 * Note: This client object keeps a local cache in-memory, so a single instance should be reused
 * throughout the lifetime of your app.
 */
class LightsparkWalletClient(coroutinesClient: LightsparkCoroutinesClient) {
    var activeWalletId: String? = null
        private set

    private val fullClient: LightsparkCoroutinesClient
    private val nodeKeyCache: NodeKeyCache
    private val requester: Requester

    init {
        fullClient = coroutinesClient
        nodeKeyCache = fullClient.nodeKeyCache
        requester = fullClient.requester
    }

    constructor(config: ClientConfig) : this(LightsparkCoroutinesClient(config))

    /**
     * Get the dashboard overview for the active lightning wallet. Includes balance info and
     * the most recent transactions.
     *
     * @param numTransactions The max number of recent transactions to fetch. Defaults to 20.
     * @return The dashboard overview for the node, including balance and recent transactions.
     * @throws LightsparkException If the wallet ID is not set yet.
     */
    suspend fun getWalletDashboard(numTransactions: Int = 20): WalletDashboard? {
        fullClient.requireValidAuth()
        return fullClient.getSingleNodeDashboard(
            requireWalletId(),
            numTransactions,
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
     * @param amountMsats The amount of the invoice in milli-satoshis.
     * @param memo Optional memo to include in the invoice.
     * @throws LightsparkException If the wallet ID is not set yet.
     */
    suspend fun createInvoice(
        amountMsats: Long,
        memo: String? = null,
    ) = fullClient.createInvoice(requireWalletId(), amountMsats, memo)

    /**
     * Pay a lightning invoice from this wallet.
     *
     * Note: This call will fail if the wallet is not unlocked yet via the [unlockWallet] function. You must
     * successfully unlock the node with its password before calling this function.
     *
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param maxFeesMsats The maximum fees to pay in milli-satoshis.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param amountMsats The amount to pay in milli-satoshis. Defaults to the full amount of the invoice.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @return The payment details.
     * @throws LightsparkException If the wallet is not unlocked yet.
     */
    suspend fun payInvoice(
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
    ): OutgoingPayment {
        if (!nodeKeyCache.contains(requireWalletId())) {
            throw LightsparkException(
                "Wallet is locked. Call unlockWallet before calling payInvoice.",
                LightsparkErrorCode.WALLET_LOCKED,
            )
        }
        return fullClient.payInvoice(
            requireWalletId(),
            encodedInvoice,
            maxFeesMsats,
            amountMsats,
            timeoutSecs,
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
     * Get the L1 fee estimate for a deposit or withdrawal.
     *
     * @return The fee estimate including a fast and minimum fee as [CurrencyAmount]s
     */
    suspend fun getBitcoinFeeEstimate() = fullClient.getBitcoinFeeEstimate()

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
            LightsparkErrorCode.MISSING_WALLET_ID,
        )
}
