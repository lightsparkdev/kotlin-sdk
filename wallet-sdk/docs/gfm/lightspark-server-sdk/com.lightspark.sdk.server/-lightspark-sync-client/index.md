//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkSyncClient](index.md)

# LightsparkSyncClient

[common]\
class [LightsparkSyncClient](index.md)(config: [ClientConfig](../-client-config/index.md))

Main entry point for the Lightspark SDK which makes synchronous, blocking API calls.

This client should only be used in environments where asynchronous calls are not possible, or where you explicitly want to block the current thread or control the concurrency yourself. Prefer using the [LightsparkCoroutinesClient](../-lightspark-coroutines-client/index.md) or LightsparkFuturesClient where possible.

```kotlin
// Initialize the client with account token info:
val lightsparkClient = LightsparkSyncClient(ClientConfig(
    authProvider = AccountApiTokenAuthProvider(
        tokenId = "your-token-id"
        token = "your-secret-token"
    )
))

// An example API call fetching the dashboard info for the active account:
val dashboard = lightsparkClient.getFullAccountDashboard()
```

or in java:

```java
// Initialize the client with account token info:
LightsparkSyncClient lightsparkClient = new LightsparkSyncClient(
  new ClientConfig()
     .setAuthProvider(new AccountApiTokenAuthProvider("your-token-id", "your-secret-token"))
);

// An example API call fetching the dashboard info for the active account:
MultiNodeDashboard dashboard = lightsparkClient.getFullAccountDashboard();
```

Note: This client object keeps a local cache in-memory, so a single instance should be reused throughout the lifetime of your app.

## Constructors

| | |
|---|---|
| [LightsparkSyncClient](-lightspark-sync-client.md) | [common]<br>fun [LightsparkSyncClient](-lightspark-sync-client.md)(config: [ClientConfig](../-client-config/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [createApiToken](create-api-token.md) | [common]<br>suspend fun [createApiToken](create-api-token.md)(name: String, transact: Boolean, testMode: Boolean): [CreateApiTokenOutput](../../com.lightspark.sdk.server.model/-create-api-token-output/index.md)<br>Create a new API token for the current account. |
| [createInvoice](create-invoice.md) | [common]<br>@JvmOverloads<br>fun [createInvoice](create-invoice.md)(nodeId: String, amountMsats: Long, memo: String? = null, type: [InvoiceType](../../com.lightspark.sdk.server.model/-invoice-type/index.md) = InvoiceType.STANDARD): [InvoiceData](../../com.lightspark.sdk.server.model/-invoice-data/index.md)<br>Creates a lightning invoice for the given node. |
| [createNodeWalletAddress](create-node-wallet-address.md) | [common]<br>fun [createNodeWalletAddress](create-node-wallet-address.md)(nodeId: String): String<br>Creates an L1 Bitcoin wallet address for a given node which can be used to deposit or withdraw funds. |
| [decodeInvoice](decode-invoice.md) | [common]<br>fun [decodeInvoice](decode-invoice.md)(encodedInvoice: String): [InvoiceData](../../com.lightspark.sdk.server.model/-invoice-data/index.md)?<br>Decode a lightning invoice to get its details included payment amount, destination, etc. |
| [deleteApiToken](delete-api-token.md) | [common]<br>fun [deleteApiToken](delete-api-token.md)(tokenId: String): Boolean<br>Delete an API token for the current account. |
| [executeQuery](execute-query.md) | [common]<br>fun &lt;[T](execute-query.md)&gt; [executeQuery](execute-query.md)(query: Query&lt;[T](execute-query.md)&gt;): [T](execute-query.md)? |
| [fundNode](fund-node.md) | [common]<br>fun [fundNode](fund-node.md)(nodeId: String, amountSats: Long?): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)<br>Adds funds to a Lightspark node on the REGTEST network. If the amount is not specified, 10,000,000 SATOSHI will be added. This API only functions for nodes created on the REGTEST network and will return an error when called for any non-REGTEST node. |
| [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md) | [common]<br>@JvmOverloads<br>fun [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)(bitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md) = defaultBitcoinNetwork): [FeeEstimate](../../com.lightspark.sdk.server.model/-fee-estimate/index.md)<br>Get the L1 fee estimate for a deposit or withdrawal. |
| [getCurrentAccount](get-current-account.md) | [common]<br>fun [getCurrentAccount](get-current-account.md)(): [Account](../../com.lightspark.sdk.server.model/-account/index.md)? |
| [getFullAccountDashboard](get-full-account-dashboard.md) | [common]<br>@JvmOverloads<br>fun [getFullAccountDashboard](get-full-account-dashboard.md)(bitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md) = defaultBitcoinNetwork, nodeIds: List&lt;String&gt;? = null): [AccountDashboard](../../com.lightspark.sdk.server.graphql/-account-dashboard/index.md)?<br>Get the dashboard overview for the active account (for the auth token that initialized this client). |
| [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md) | [common]<br>@JvmOverloads<br>suspend fun [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md)(nodeId: String, encodedPaymentRequest: String, amountMsats: Long? = null): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)<br>Gets an estimate of the fees that will be paid for a Lightning invoice. |
| [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md) | [common]<br>suspend fun [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)(nodeId: String, destinationNodePublicKey: String, amountMsats: Long): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)<br>Returns an estimate of the fees that will be paid to send a payment to another Lightning node. |
| [getSingleNodeDashboard](get-single-node-dashboard.md) | [common]<br>@JvmOverloads<br>fun [getSingleNodeDashboard](get-single-node-dashboard.md)(nodeId: String, numTransactions: Int = 20, bitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md) = defaultBitcoinNetwork): [WalletDashboard](../../com.lightspark.sdk.server.graphql/-wallet-dashboard/index.md)?<br>Get the dashboard overview for a single node as a lightning wallet. Includes balance info and the most recent transactions. |
| [loadNodeSigningKey](load-node-signing-key.md) | [common]<br>fun [loadNodeSigningKey](load-node-signing-key.md)(nodeId: String, signingKeyBytesPEM: ByteArray)<br>Unlocks a node for use with the SDK for the current application session. This function or [recoverNodeSigningKey](recover-node-signing-key.md) must be called before any other functions that require node signing keys, including [payInvoice](pay-invoice.md). |
| [payInvoice](pay-invoice.md) | [common]<br>@JvmOverloads<br>fun [payInvoice](pay-invoice.md)(nodeId: String, encodedInvoice: String, maxFeesMsats: Long, amountMsats: Long? = null, timeoutSecs: Int = 60): [OutgoingPayment](../../com.lightspark.sdk.server.model/-outgoing-payment/index.md)<br>Pay a lightning invoice for the given node. |
| [recoverNodeSigningKey](recover-node-signing-key.md) | [common]<br>suspend fun [recoverNodeSigningKey](recover-node-signing-key.md)(nodeId: String, nodePassword: String): Boolean<br>Unlocks a node for use with the SDK for the current application session. This function must be called before any other functions that require node signing keys, including [payInvoice](pay-invoice.md). |
| [requestWithdrawal](request-withdrawal.md) | [common]<br>fun [requestWithdrawal](request-withdrawal.md)(nodeId: String, amountSats: Long, bitcoinAddress: String, mode: [WithdrawalMode](../../com.lightspark.sdk.server.model/-withdrawal-mode/index.md)): [WithdrawalRequest](../../com.lightspark.sdk.server.model/-withdrawal-request/index.md)<br>Withdraws funds from the account and sends it to the requested bitcoin address. |
| [sendPayment](send-payment.md) | [common]<br>@JvmOverloads<br>fun [sendPayment](send-payment.md)(payerNodeId: String, destinationPublicKey: String, amountMsats: Long, maxFeesMsats: Long, timeoutSecs: Int? = null): [OutgoingPayment](../../com.lightspark.sdk.server.model/-outgoing-payment/index.md)<br>Sends a payment directly to a node on the Lightning Network through the public key of the node without an invoice. |
| [setAccountApiToken](set-account-api-token.md) | [common]<br>fun [setAccountApiToken](set-account-api-token.md)(tokenId: String, tokenSecret: String)<br>Set the account API token info for this client, thereby overriding the auth token provider to use account-based authentication. |
| [setAuthProvider](set-auth-provider.md) | [common]<br>fun [setAuthProvider](set-auth-provider.md)(authProvider: AuthProvider)<br>Override the auth token provider for this client to provide custom headers on all API calls. |
| [setBitcoinNetwork](set-bitcoin-network.md) | [common]<br>fun [setBitcoinNetwork](set-bitcoin-network.md)(network: [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md)) |