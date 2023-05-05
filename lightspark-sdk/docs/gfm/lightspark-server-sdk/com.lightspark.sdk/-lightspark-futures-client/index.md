//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkFuturesClient](index.md)

# LightsparkFuturesClient

[commonJvmAndroid]\
class [LightsparkFuturesClient](index.md)(config: ClientConfig)

Main entry point for the Lightspark SDK using the Java Futures API.

```kotlin
// Initialize the client with account token info:
val lightsparkClient = LightsparkFuturesClient(ClientConfig(
    authProvider = AccountApiTokenAuthProvider(
        tokenId = "your-token-id"
        token = "your-secret-token"
    )
))

// An example API call fetching the dashboard info for the active account:
val dashboard = lightsparkClient.getFullAccountDashboard().await()
```

or in java:

```java
// Initialize the client with account token info:
LightsparkFuturesClient lightsparkClient = new LightsparkFuturesClient(
  new ClientConfig()
     .setAuthProvider(new AccountApiTokenAuthProvider("your-token-id", "your-secret-token"))
);

// An example API call fetching the dashboard info for the active account:
MultiNodeDashboard dashboard = lightsparkClient.getFullAccountDashboard().await();
```

Note: This client object keeps a local cache in-memory, so a single instance should be reused throughout the lifetime of your app.

## Constructors

| | |
|---|---|
| [LightsparkFuturesClient](-lightspark-futures-client.md) | [commonJvmAndroid]<br>fun [LightsparkFuturesClient](-lightspark-futures-client.md)(config: ClientConfig) |

## Functions

| Name | Summary |
|---|---|
| [createApiToken](create-api-token.md) | [commonJvmAndroid]<br>suspend fun [createApiToken](create-api-token.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), transact: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), testMode: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): &lt;Error class: unknown class&gt;&lt;CreateApiTokenOutput&gt;<br>Create a new API token for the current account. |
| [createInvoice](create-invoice.md) | [commonJvmAndroid]<br>fun [createInvoice](create-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, type: InvoiceType = InvoiceType.STANDARD): &lt;Error class: unknown class&gt;&lt;InvoiceData&gt;<br>Creates a lightning invoice for the given node. |
| [createNodeWalletAddress](create-node-wallet-address.md) | [commonJvmAndroid]<br>fun [createNodeWalletAddress](create-node-wallet-address.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Creates an L1 Bitcoin wallet address for a given node which can be used to deposit or withdraw funds. |
| [decodeInvoice](decode-invoice.md) | [commonJvmAndroid]<br>fun [decodeInvoice](decode-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;InvoiceData?&gt;<br>Decode a lightning invoice to get its details included payment amount, destination, etc. |
| [deleteApiToken](delete-api-token.md) | [commonJvmAndroid]<br>fun [deleteApiToken](delete-api-token.md)(tokenId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;<br>Delete an API token for the current account. |
| [executeQuery](execute-query.md) | [commonJvmAndroid]<br>fun &lt;[T](execute-query.md)&gt; [executeQuery](execute-query.md)(query: [Query](../../com.lightspark.sdk.requester/-query/index.md#-168528822%2FExtensions%2F1046503175)&lt;[T](execute-query.md)&gt;): &lt;Error class: unknown class&gt;&lt;[T](execute-query.md)?&gt; |
| [fundNode](fund-node.md) | [commonJvmAndroid]<br>fun [fundNode](fund-node.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountSats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;<br>Adds funds to a Lightspark node on the REGTEST network. If the amount is not specified, 10,000,000 SATOSHI will be added. This API only functions for nodes created on the REGTEST network and will return an error when called for any non-REGTEST node. |
| [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md) | [commonJvmAndroid]<br>fun [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)(bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork): &lt;Error class: unknown class&gt;&lt;FeeEstimate&gt;<br>Get the L1 fee estimate for a deposit or withdrawal. |
| [getCurrentAccount](get-current-account.md) | [commonJvmAndroid]<br>fun [getCurrentAccount](get-current-account.md)(): &lt;Error class: unknown class&gt;&lt;Account?&gt; |
| [getFullAccountDashboard](get-full-account-dashboard.md) | [commonJvmAndroid]<br>fun [getFullAccountDashboard](get-full-account-dashboard.md)(bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): &lt;Error class: unknown class&gt;&lt;AccountDashboard?&gt;<br>Get the dashboard overview for the active account (for the auth token that initialized this client). |
| [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md) | [commonJvmAndroid]<br>suspend fun [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;<br>Gets an estimate of the fees that will be paid for a Lightning invoice. |
| [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md) | [commonJvmAndroid]<br>suspend fun [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinationNodePublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;<br>Returns an estimate of the fees that will be paid to send a payment to another Lightning node. |
| [getSingleNodeDashboard](get-single-node-dashboard.md) | [commonJvmAndroid]<br>fun [getSingleNodeDashboard](get-single-node-dashboard.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork): &lt;Error class: unknown class&gt;&lt;WalletDashboard?&gt;<br>Get the dashboard overview for a single node as a lightning wallet. Includes balance info and the most recent transactions. |
| [loadNodeSigningKey](load-node-signing-key.md) | [commonJvmAndroid]<br>fun [loadNodeSigningKey](load-node-signing-key.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), signingKeyBytesPEM: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html))<br>Unlocks a node for use with the SDK for the current application session. This function or [recoverNodeSigningKey](recover-node-signing-key.md) must be called before any other functions that require node signing keys, including [payInvoice](pay-invoice.md). |
| [payInvoice](pay-invoice.md) | [commonJvmAndroid]<br>fun [payInvoice](pay-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60): &lt;Error class: unknown class&gt;&lt;OutgoingPayment&gt;<br>Pay a lightning invoice for the given node. |
| [recoverNodeSigningKey](recover-node-signing-key.md) | [commonJvmAndroid]<br>suspend fun [recoverNodeSigningKey](recover-node-signing-key.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), nodePassword: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;<br>Unlocks a node for use with the SDK for the current application session. This function must be called before any other functions that require node signing keys, including [payInvoice](pay-invoice.md). |
| [requestWithdrawal](request-withdrawal.md) | [commonJvmAndroid]<br>fun [requestWithdrawal](request-withdrawal.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountSats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), bitcoinAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), mode: WithdrawalMode): &lt;Error class: unknown class&gt;&lt;WithdrawalRequest&gt;<br>Withdraws funds from the account and sends it to the requested bitcoin address. |
| [sendPayment](send-payment.md) | [commonJvmAndroid]<br>fun [sendPayment](send-payment.md)(payerNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinationPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null): &lt;Error class: unknown class&gt;&lt;OutgoingPayment&gt;<br>Sends a payment directly to a node on the Lightning Network through the public key of the node without an invoice. |
| [setAccountApiToken](set-account-api-token.md) | [commonJvmAndroid]<br>fun [setAccountApiToken](set-account-api-token.md)(tokenId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tokenSecret: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set the account API token info for this client, thereby overriding the auth token provider to use account-based authentication. |
| [setAuthProvider](set-auth-provider.md) | [commonJvmAndroid]<br>fun [setAuthProvider](set-auth-provider.md)(authProvider: AuthProvider)<br>Override the auth token provider for this client to provide custom headers on all API calls. |
| [setBitcoinNetwork](set-bitcoin-network.md) | [commonJvmAndroid]<br>fun [setBitcoinNetwork](set-bitcoin-network.md)(network: BitcoinNetwork) |
