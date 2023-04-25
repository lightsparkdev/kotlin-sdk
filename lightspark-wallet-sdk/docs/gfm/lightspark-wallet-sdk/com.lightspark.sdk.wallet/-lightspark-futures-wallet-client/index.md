//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkFuturesWalletClient](index.md)

# LightsparkFuturesWalletClient

[commonJvmAndroid]\
class [LightsparkFuturesWalletClient](index.md)(config: ClientConfig)

Main entry point for the Lightspark SDK using the Java Futures API.

This client is meant to be used in Java codebases that do not support Kotlin coroutines.

```java
// Initialize the client with oauth:
OAuthHelper oAuthHelper = new OAuthHelper(applicationContext);
LightsparkFuturesWalletClient lightsparkClient = new LightsparkFuturesWalletClient(new ClientConfig(
    authProvider = new OAuthProvider(oAuthHelper)
));

// An example API call fetching the dashboard info for the active account:
WalletDashboard dashboard = lightsparkClient.getWalletDashboard().get(5, TimeUnit.SECONDS);
```

Note: This client object keeps a local cache in-memory, so a single instance should be reused throughout the lifetime of your app.

## Constructors

| | |
|---|---|
| [LightsparkFuturesWalletClient](-lightspark-futures-wallet-client.md) | [commonJvmAndroid]<br>fun [LightsparkFuturesWalletClient](-lightspark-futures-wallet-client.md)(config: ClientConfig) |

## Functions

| Name | Summary |
|---|---|
| [createBitcoinFundingAddress](create-bitcoin-funding-address.md) | [commonJvmAndroid]<br>fun [createBitcoinFundingAddress](create-bitcoin-funding-address.md)(): &lt;Error class: unknown class&gt;&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Creates an L1 Bitcoin wallet address which can be used to deposit or withdraw funds from the Lightning wallet. |
| [createInvoice](create-invoice.md) | [commonJvmAndroid]<br>fun [createInvoice](create-invoice.md)(amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, type: InvoiceType = InvoiceType.STANDARD): &lt;Error class: unknown class&gt;&lt;InvoiceData&gt;<br>Creates a lightning invoice for the current wallet. |
| [decodeInvoice](decode-invoice.md) | [commonJvmAndroid]<br>fun [decodeInvoice](decode-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;InvoiceData?&gt;<br>Decode a lightning invoice to get its details included payment amount, destination, etc. |
| [deployWallet](deploy-wallet.md) | [commonJvmAndroid]<br>suspend fun [deployWallet](deploy-wallet.md)(): &lt;Error class: unknown class&gt;&lt;Wallet?&gt;<br>Deploys a wallet in the Lightspark infrastructure. This is an asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will change to `DEPLOYED` (or `FAILED`). |
| [executeQuery](execute-query.md) | [commonJvmAndroid]<br>fun &lt;[T](execute-query.md)&gt; [executeQuery](execute-query.md)(query: Query&lt;[T](execute-query.md)&gt;): &lt;Error class: unknown class&gt;&lt;[T](execute-query.md)?&gt;<br>Executes a raw graphql query against the server. |
| [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md) | [commonJvmAndroid]<br>fun [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)(): &lt;Error class: unknown class&gt;&lt;FeeEstimate&gt;<br>Get the L1 fee estimate for a deposit or withdrawal. |
| [getCurrentWallet](get-current-wallet.md) | [commonJvmAndroid]<br>fun [getCurrentWallet](get-current-wallet.md)(): &lt;Error class: unknown class&gt;&lt;Wallet?&gt; |
| [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md) | [commonJvmAndroid]<br>suspend fun [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md)(encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;<br>Gets an estimate of the fees that will be paid for a Lightning invoice. |
| [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md) | [commonJvmAndroid]<br>suspend fun [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)(destinationNodePublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;<br>Returns an estimate of the fees that will be paid to send a payment to another Lightning node. |
| [getWalletDashboard](get-wallet-dashboard.md) | [commonJvmAndroid]<br>fun [getWalletDashboard](get-wallet-dashboard.md)(numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, numPaymentRequests: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20): &lt;Error class: unknown class&gt;&lt;WalletDashboard?&gt;<br>Get the dashboard overview for a Lightning wallet. Includes balance info and the most recent transactions and payment requests. |
| [initializeWallet](initialize-wallet.md) | [commonJvmAndroid]<br>suspend fun [initializeWallet](initialize-wallet.md)(keyType: KeyType, signingPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;Wallet?&gt;<br>Initializes a wallet in the Lightspark infrastructure and syncs it to the Bitcoin network. This is an asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will change to `READY` (or `FAILED`). |
| [isWalletUnlocked](is-wallet-unlocked.md) | [commonJvmAndroid]<br>fun [isWalletUnlocked](is-wallet-unlocked.md)(): &lt;Error class: unknown class&gt;&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [loadWalletSigningKey](load-wallet-signing-key.md) | [commonJvmAndroid]<br>fun [loadWalletSigningKey](load-wallet-signing-key.md)(signingKeyBytesPEM: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html))<br>Unlocks the wallet for use with the SDK for the current application session. This function must be called before any other functions that require wallet signing keys, including [payInvoice](pay-invoice.md). |
| [loginWithJWT](login-with-j-w-t.md) | [commonJvmAndroid]<br>fun [loginWithJWT](login-with-j-w-t.md)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), jwt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;LoginWithJWTOutput&gt;<br>Login using the Custom JWT authentication scheme described in our documentation. |
| [payInvoice](pay-invoice.md) | [commonJvmAndroid]<br>fun [payInvoice](pay-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60): &lt;Error class: unknown class&gt;&lt;OutgoingPayment&gt;<br>Pay a lightning invoice from the current wallet. |
| [requestWithdrawal](request-withdrawal.md) | [commonJvmAndroid]<br>fun [requestWithdrawal](request-withdrawal.md)(amountSats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), bitcoinAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;WithdrawalRequest&gt;<br>Withdraws funds from the account and sends it to the requested bitcoin address. |
| [sendPayment](send-payment.md) | [commonJvmAndroid]<br>fun [sendPayment](send-payment.md)(destinationPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60): &lt;Error class: unknown class&gt;&lt;OutgoingPayment&gt;<br>Sends a payment directly to a node on the Lightning Network through the public key of the node without an invoice. |
| [setAuthProvider](set-auth-provider.md) | [commonJvmAndroid]<br>fun [setAuthProvider](set-auth-provider.md)(authProvider: AuthProvider)<br>Override the auth token provider for this client to provide custom headers on all API calls. |
| [terminateWallet](terminate-wallet.md) | [commonJvmAndroid]<br>suspend fun [terminateWallet](terminate-wallet.md)(): &lt;Error class: unknown class&gt;&lt;Wallet?&gt;<br>Removes the wallet from Lightspark infrastructure. It won't be connected to the Lightning network anymore and its funds won't be accessible outside of the Funds Recovery Kit process. |
