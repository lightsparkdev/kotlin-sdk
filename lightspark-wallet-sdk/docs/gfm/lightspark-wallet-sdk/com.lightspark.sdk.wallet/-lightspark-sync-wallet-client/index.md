//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkSyncWalletClient](index.md)

# LightsparkSyncWalletClient

[common]\
class [LightsparkSyncWalletClient](index.md)(config: [ClientConfig](../-client-config/index.md))

Main entry point for the Lightspark SDK which makes synchronous, blocking API calls.

This client should only be used in environments where asynchronous calls are not possible, or where you explicitly want to block the current thread or control the concurrency yourself. Prefer using the [LightsparkCoroutinesWalletClient](../-lightspark-coroutines-wallet-client/index.md) or LightsparkFuturesWalletClient where possible.

```kotlin
// Initialize the client with oauth:
val oAuthHelper = OauthHelper(applicationContext)
val lightsparkClient = LightsparkSyncWalletClient(ClientConfig(
    authProvider = OAuthProvider(oAuthHelper)
))

// An example API call fetching the dashboard info for the active account:
val dashboard = lightsparkClient.getWalletDashboard()
```

or in java:

```java
// Initialize the client with oauth:
OAuthHelper oAuthHelper = new OAuthHelper(applicationContext);
LightsparkSyncWalletClient lightsparkClient = new LightsparkSyncWalletClient(new ClientConfig(
    authProvider = new OAuthProvider(oAuthHelper)
));

// An example API call fetching the dashboard info for the active account:
WalletDashboard dashboard = lightsparkClient.getWalletDashboard();
```

Note: This client object keeps a local cache in-memory, so a single instance should be reused throughout the lifetime of your app.

## Constructors

| | |
|---|---|
| [LightsparkSyncWalletClient](-lightspark-sync-wallet-client.md) | [common]<br>fun [LightsparkSyncWalletClient](-lightspark-sync-wallet-client.md)(config: [ClientConfig](../-client-config/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [createBitcoinFundingAddress](create-bitcoin-funding-address.md) | [common]<br>fun [createBitcoinFundingAddress](create-bitcoin-funding-address.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Creates an L1 Bitcoin wallet address which can be used to deposit or withdraw funds from the Lightning wallet. |
| [createInvoice](create-invoice.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [createInvoice](create-invoice.md)(amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, type: [InvoiceType](../../com.lightspark.sdk.wallet.model/-invoice-type/index.md) = InvoiceType.STANDARD): [InvoiceData](../../com.lightspark.sdk.wallet.model/-invoice-data/index.md)<br>Creates a lightning invoice for the current wallet. |
| [decodeInvoice](decode-invoice.md) | [common]<br>fun [decodeInvoice](decode-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [InvoiceData](../../com.lightspark.sdk.wallet.model/-invoice-data/index.md)?<br>Decode a lightning invoice to get its details included payment amount, destination, etc. |
| [deployWallet](deploy-wallet.md) | [common]<br>fun [deployWallet](deploy-wallet.md)(): [Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)?<br>Deploys a wallet in the Lightspark infrastructure. This is an asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will change to `DEPLOYED` (or `FAILED`). |
| [executeQuery](execute-query.md) | [common]<br>fun &lt;[T](execute-query.md)&gt; [executeQuery](execute-query.md)(query: Query&lt;[T](execute-query.md)&gt;): [T](execute-query.md)?<br>Executes a raw graphql query against the server. |
| [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md) | [common]<br>fun [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)(): [FeeEstimate](../../com.lightspark.sdk.wallet.model/-fee-estimate/index.md)<br>Get the L1 fee estimate for a deposit or withdrawal. |
| [getCurrentWallet](get-current-wallet.md) | [common]<br>fun [getCurrentWallet](get-current-wallet.md)(): [Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)? |
| [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>suspend fun [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md)(encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): [CurrencyAmount](../../com.lightspark.sdk.wallet.model/-currency-amount/index.md)<br>Gets an estimate of the fees that will be paid for a Lightning invoice. |
| [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md) | [common]<br>suspend fun [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)(destinationNodePublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [CurrencyAmount](../../com.lightspark.sdk.wallet.model/-currency-amount/index.md)<br>Returns an estimate of the fees that will be paid to send a payment to another Lightning node. |
| [getWalletDashboard](get-wallet-dashboard.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getWalletDashboard](get-wallet-dashboard.md)(numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, numPaymentRequests: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20): [WalletDashboard](../../com.lightspark.sdk.wallet.graphql/-wallet-dashboard/index.md)?<br>Get the dashboard overview for a Lightning wallet. Includes balance info and the most recent transactions and payment requests. |
| [initializeWallet](initialize-wallet.md) | [common]<br>fun [initializeWallet](initialize-wallet.md)(keyType: [KeyType](../../com.lightspark.sdk.wallet.model/-key-type/index.md), signingPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)?<br>Initializes a wallet in the Lightspark infrastructure and syncs it to the Bitcoin network. This is an asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will change to `READY` (or `FAILED`). |
| [isWalletUnlocked](is-wallet-unlocked.md) | [common]<br>fun [isWalletUnlocked](is-wallet-unlocked.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [loadWalletSigningKey](load-wallet-signing-key.md) | [common]<br>fun [loadWalletSigningKey](load-wallet-signing-key.md)(signingKeyBytesPEM: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html))<br>Unlocks the wallet for use with the SDK for the current application session. This function must be called before any other functions that require wallet signing keys, including [payInvoice](pay-invoice.md). |
| [loginWithJWT](login-with-j-w-t.md) | [common]<br>fun [loginWithJWT](login-with-j-w-t.md)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), jwt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LoginWithJWTOutput](../../com.lightspark.sdk.wallet.model/-login-with-j-w-t-output/index.md)<br>Login using the Custom JWT authentication scheme described in our documentation. |
| [payInvoice](pay-invoice.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [payInvoice](pay-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60): [OutgoingPayment](../../com.lightspark.sdk.wallet.model/-outgoing-payment/index.md)<br>Pay a lightning invoice from the current wallet. |
| [requestWithdrawal](request-withdrawal.md) | [common]<br>fun [requestWithdrawal](request-withdrawal.md)(amountSats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), bitcoinAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [WithdrawalRequest](../../com.lightspark.sdk.wallet.model/-withdrawal-request/index.md)<br>Withdraws funds from the account and sends it to the requested bitcoin address. |
| [sendPayment](send-payment.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [sendPayment](send-payment.md)(destinationPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60): [OutgoingPayment](../../com.lightspark.sdk.wallet.model/-outgoing-payment/index.md)<br>Sends a payment directly to a node on the Lightning Network through the public key of the node without an invoice. |
| [setAuthProvider](set-auth-provider.md) | [common]<br>fun [setAuthProvider](set-auth-provider.md)(authProvider: AuthProvider)<br>Override the auth token provider for this client to provide custom headers on all API calls. |
| [terminateWallet](terminate-wallet.md) | [common]<br>fun [terminateWallet](terminate-wallet.md)(): [Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)?<br>Removes the wallet from Lightspark infrastructure. It won't be connected to the Lightning network anymore and its funds won't be accessible outside of the Funds Recovery Kit process. |
