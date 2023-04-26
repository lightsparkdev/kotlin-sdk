package com.lightspark.sdk.wallet

import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.core.auth.AuthProvider
import com.lightspark.sdk.core.auth.LightsparkAuthenticationException
import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.wallet.auth.*
import com.lightspark.sdk.wallet.auth.jwt.JwtStorage
import com.lightspark.sdk.wallet.graphql.*
import com.lightspark.sdk.wallet.model.*
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmOverloads
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*

/**
 * Main entry point for the Lightspark SDK which makes synchronous, blocking API calls.
 *
 * This client should only be used in environments where asynchronous calls are not possible, or where you explicitly
 * want to block the current thread or control the concurrency yourself. Prefer using the [LightsparkCoroutinesWalletClient]
 * or [LightsparkFuturesWalletClient] where possible.
 *
 * ```kotlin
 * // Initialize the client with oauth:
 * val oAuthHelper = OauthHelper(applicationContext)
 * val lightsparkClient = LightsparkSyncWalletClient(ClientConfig(
 *     authProvider = OAuthProvider(oAuthHelper)
 * ))
 *
 * // An example API call fetching the dashboard info for the active account:
 * val dashboard = lightsparkClient.getWalletDashboard()
 * ```
 *
 * or in java:
 * ```java
 * // Initialize the client with oauth:
 * OAuthHelper oAuthHelper = new OAuthHelper(applicationContext);
 * LightsparkSyncWalletClient lightsparkClient = new LightsparkSyncWalletClient(new ClientConfig(
 *     authProvider = new OAuthProvider(oAuthHelper)
 * ));
 *
 * // An example API call fetching the dashboard info for the active account:
 * WalletDashboard dashboard = lightsparkClient.getWalletDashboard();
 * ```
 *
 * Note: This client object keeps a local cache in-memory, so a single instance should be reused
 * throughout the lifetime of your app.
 */
class LightsparkSyncWalletClient constructor(config: ClientConfig) {
    private val asyncClient: LightsparkCoroutinesWalletClient = LightsparkCoroutinesWalletClient(config)

    /**
     * Override the auth token provider for this client to provide custom headers on all API calls.
     */
    fun setAuthProvider(authProvider: AuthProvider) {
        asyncClient.setAuthProvider(authProvider)
    }

    /**
     * Login using the Custom JWT authentication scheme described in our documentation.
     *
     * Note: When using this method, you are responsible for refreshing the JWT token before or when it expires. If the
     * token expires, the client will throw a [LightsparkAuthenticationException] on the next API call which requires
     * valid authentication. Then you'll need to call this method again to get a new token.
     *
     * @param accountId The account ID to login with. This is specific to your company's account.
     * @param jwt The JWT to use for authentication of this user.
     * * @param storage A [JwtStorage] implementation that will store the new JWT token info.
     * @return The output of the login operation, including the access token, expiration time, and wallet info.
     * @throws LightsparkException if the login fails.
     */
    @Throws(LightsparkException::class, CancellationException::class)
    fun loginWithJWT(accountId: String, jwt: String, storage: JwtStorage): LoginWithJWTOutput =
        runBlocking { asyncClient.loginWithJWT(accountId, jwt, storage) }

    /**
     * Deploys a wallet in the Lightspark infrastructure. This is an asynchronous operation, the caller should then poll
     * the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will
     * change to `DEPLOYED` (or `FAILED`).
     *
     * @return The wallet that was deployed.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    fun deployWallet(): Wallet? = runBlocking { asyncClient.deployWallet() }

    /**
     * Initializes a wallet in the Lightspark infrastructure and syncs it to the Bitcoin network. This is an
     * asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications).
     * When this process is over, the Wallet status will change to `READY` (or `FAILED`).
     *
     * @param keyType The type of key to use for the wallet.
     * @param signingPublicKey The base64-encoded public key to use for signing transactions.
     * @return The wallet that was initialized.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    fun initializeWallet(keyType: KeyType, signingPublicKey: String): Wallet? = runBlocking {
        asyncClient.initializeWallet(keyType, signingPublicKey)
    }

    /**
     * Removes the wallet from Lightspark infrastructure. It won't be connected to the Lightning network anymore and
     * its funds won't be accessible outside of the Funds Recovery Kit process.
     *
     * @return The wallet that was terminated.
     * @throws LightsparkAuthenticationException if there is no valid authentication.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    fun terminateWallet(): Wallet? = runBlocking { asyncClient.terminateWallet() }

    /**
     * Get the dashboard overview for a Lightning wallet. Includes balance info and
     * the most recent transactions and payment requests.
     *
     * @param numTransactions The max number of recent transactions to fetch. Defaults to 20.
     * @param numPaymentRequests The max number of recent payment requests to fetch. Defaults to 20.
     * @return The dashboard overview for the wallet, including balance and recent transactions and payment requests.
     * @throws LightsparkAuthenticationException If the user is not authenticated.
     */
    @JvmOverloads
    @Throws(CancellationException::class, LightsparkAuthenticationException::class)
    fun getWalletDashboard(
        numTransactions: Int = 20,
        numPaymentRequests: Int = 20,
    ): WalletDashboard? = runBlocking { asyncClient.getWalletDashboard(numTransactions, numPaymentRequests) }

    /**
     * Creates a lightning invoice for the current wallet.
     *
     * @param amountMsats The amount of the invoice in milli-satoshis.
     * @param memo Optional memo to include in the invoice.
     * @param type The type of invoice to create. Defaults to [InvoiceType.STANDARD].
     * @return The invoice data.
     * @throws LightsparkAuthenticationException If the user is not authenticated.
     */
    @JvmOverloads
    @Throws(CancellationException::class, LightsparkAuthenticationException::class)
    fun createInvoice(
        amountMsats: Long,
        memo: String? = null,
        type: InvoiceType = InvoiceType.STANDARD,
    ): InvoiceData = runBlocking { asyncClient.createInvoice(amountMsats, memo, type) }

    /**
     * Pay a lightning invoice from the current wallet.
     *
     * Note: This call will fail if the wallet is not unlocked yet via [loadWalletSigningKey]. You must successfully
     * unlock the wallet before calling this function.
     *
     * @param encodedInvoice An encoded string representation of the invoice to pay.
     * @param maxFeesMsats The maximum fees to pay in milli-satoshis. You must pass a value.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param amountMsats The amount to pay in milli-satoshis. Defaults to the full amount of the invoice.
     * @param timeoutSecs The number of seconds to wait for the payment to complete. Defaults to 60.
     * @return The payment details.
     * @throws LightsparkException If the payment fails or if the wallet is locked.
     */
    @JvmOverloads
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, CancellationException::class)
    fun payInvoice(
        encodedInvoice: String,
        maxFeesMsats: Long,
        amountMsats: Long? = null,
        timeoutSecs: Int = 60,
    ): OutgoingPayment =
        runBlocking { asyncClient.payInvoice(encodedInvoice, maxFeesMsats, amountMsats, timeoutSecs) }

    /**
     * Decode a lightning invoice to get its details included payment amount, destination, etc.
     *
     * @param encodedInvoice An encoded string representation of the invoice to decode.
     * @return The decoded invoice details.
     */
    fun decodeInvoice(encodedInvoice: String): InvoiceData? = runBlocking { asyncClient.decodeInvoice(encodedInvoice) }

    /**
     * Unlocks the wallet for use with the SDK for the current application session. This function
     * must be called before any other functions that require wallet signing keys, including [payInvoice].
     *
     * This function is intended for use in cases where the wallet's private signing key is already saved by the
     * application outside of the SDK. It is the responsibility of the application to ensure that the key is valid and
     * that it is the correct key for the wallet. Otherwise signed requests will fail.
     *
     * @param signingKeyBytesPEM The PEM encoded bytes of the wallet's private signing key.
     * @throws LightsparkAuthenticationException if there is no valid auth.
     */
    @Throws(LightsparkAuthenticationException::class)
    fun loadWalletSigningKey(signingKeyBytesPEM: ByteArray) = asyncClient.loadWalletSigningKey(signingKeyBytesPEM)

    /**
     * Get the L1 fee estimate for a deposit or withdrawal.
     *
     * @return The fee estimate including a fast and minimum fee as [CurrencyAmount]s
     */
    fun getBitcoinFeeEstimate(): FeeEstimate = runBlocking { asyncClient.getBitcoinFeeEstimate() }

    /**
     * Returns an estimate of the fees that will be paid to send a payment to another Lightning node.
     *
     * @param destinationNodePublicKey The public key of the node that you want to pay.
     * @param amountMsats The payment amount expressed in msats.
     * @returns An estimate of the fees that will be paid to send a payment to another Lightning node.
     * @throws LightsparkAuthenticationException If the user is not authenticated.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun getLightningFeeEstimateForNode(
        destinationNodePublicKey: String,
        amountMsats: Long,
    ): CurrencyAmount =
        runBlocking { asyncClient.getLightningFeeEstimateForNode(destinationNodePublicKey, amountMsats) }

    /**
     * Gets an estimate of the fees that will be paid for a Lightning invoice.
     *
     * @param encodedPaymentRequest The invoice you want to pay (as defined by the BOLT11 standard).
     * @param amountMsats If the invoice does not specify a payment amount, then the amount that you wish to pay,
     *     expressed in msats.
     * @returns An estimate of the fees that will be paid for a Lightning invoice.
     * @throws LightsparkAuthenticationException If the user is not authenticated.
     */
    @JvmOverloads
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    suspend fun getLightningFeeEstimateForInvoice(
        encodedPaymentRequest: String,
        amountMsats: Long? = null,
    ): CurrencyAmount =
        runBlocking { asyncClient.getLightningFeeEstimateForInvoice(encodedPaymentRequest, amountMsats) }

    /**
     * Creates an L1 Bitcoin wallet address which can be used to deposit or withdraw funds from the Lightning wallet.
     *
     * @return The newly created L1 wallet address.
     * @throws LightsparkException if the wallet is locked or if there's no valid auth.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, CancellationException::class)
    fun createBitcoinFundingAddress(): String = runBlocking { asyncClient.createBitcoinFundingAddress() }

    /**
     * @return The current wallet if one exists, null otherwise.
     */
    @Throws(LightsparkAuthenticationException::class, CancellationException::class)
    fun getCurrentWallet(): Wallet? = runBlocking { asyncClient.getCurrentWallet() }

    /**
     * Withdraws funds from the account and sends it to the requested bitcoin address.
     *
     * The process is asynchronous and may take up to a few minutes. You can check the progress by polling the
     * `WithdrawalRequest` that is created, or by subscribing to a webhook.
     *
     * @param amountSats The amount of funds to withdraw in SATOSHI.
     * @param bitcoinAddress The Bitcoin address to withdraw funds to.
     * @throws LightsparkException if the withdrawal failed or if the wallet is locked.
     */
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, CancellationException::class)
    fun requestWithdrawal(
        amountSats: Long,
        bitcoinAddress: String,
    ): WithdrawalRequest = runBlocking { asyncClient.requestWithdrawal(amountSats, bitcoinAddress) }

    /**
     * Sends a payment directly to a node on the Lightning Network through the public key of the node without an invoice.
     *
     * @param destinationPublicKey The public key of the destination node.
     * @param amountMsats The amount to pay in milli-satoshis.
     * @param maxFeesMsats The maximum amount of fees that you want to pay for this payment to be sent.
     *     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,
     *     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats.
     * @param timeoutSecs The timeout in seconds that we will try to make the payment.
     * @return An `OutgoingPayment` object if the payment was successful, or throws if the payment failed.
     * @throws LightsparkException if the payment failed or if the wallet is locked.
     */
    @JvmOverloads
    @Throws(LightsparkException::class, LightsparkAuthenticationException::class, CancellationException::class)
    fun sendPayment(
        destinationPublicKey: String,
        amountMsats: Long,
        maxFeesMsats: Long,
        timeoutSecs: Int = 60,
    ): OutgoingPayment = runBlocking {
        asyncClient.sendPayment(
            destinationPublicKey,
            amountMsats,
            maxFeesMsats,
            timeoutSecs,
        )
    }

    /**
     * @return True if the wallet is unlocked or false if it is locked.
     */
    fun isWalletUnlocked(): Boolean = runBlocking { asyncClient.isWalletUnlocked() }

    // TODO: Add support for the transaction subscription query.

    /**
     * Executes a raw graphql query against the server.
     */
    fun <T> executeQuery(query: Query<T>): T? = runBlocking { asyncClient.executeQuery(query) }
}

fun <T> Query<T>.executeSync(client: LightsparkSyncWalletClient): T? = client.executeQuery(this)
