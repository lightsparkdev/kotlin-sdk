//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkClient](index.md)

# LightsparkClient

[common]\
class [LightsparkClient](index.md)

Main entry point for the Lightspark SDK.

Should be constructed using the Builder class.

```kotlin
// Initialize the client with account token info:
val lightsparkClient = LightsparkClient.Builder().apply {
    tokenId = "your-token-id"
    token = "your-secret-token"
}.build()

// An example API call fetching the dashboard info for the active account:
val dashboard = lightsparkClient.getFullAccountDashboard()
```

Note: This client object keeps a local cache in-memory, so a single instance should be reused throughout the lifetime of your app.

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [common]<br>class [Builder](-builder/index.md)<br>The Builder class for [LightsparkClient](index.md) and the main entry point for the SDK. |

## Functions

| Name | Summary |
|---|---|
| [createInvoice](create-invoice.md) | [common]<br>suspend fun [createInvoice](create-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amount: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): CreateInvoiceMutation.Invoice<br>Creates a lightning invoice for the given node. |
| [decodeInvoice](decode-invoice.md) | [common]<br>suspend fun [decodeInvoice](decode-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): DecodedPaymentRequestQuery.OnInvoiceData?<br>Decode a lightning invoice to get its details included payment amount, destination, etc. |
| [getFeeEstimate](get-fee-estimate.md) | [common]<br>suspend fun [getFeeEstimate](get-fee-estimate.md)(bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)): [FeeEstimate](../../com.lightspark.sdk.model/-fee-estimate/index.md)<br>Get the fee estimate for a payment. |
| [getFullAccountDashboard](get-full-account-dashboard.md) | [common]<br>suspend fun [getFullAccountDashboard](get-full-account-dashboard.md)(bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK), afterDate: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? = null, beforeDate: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? = null, nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): DashboardOverviewQuery.Current_account?<br>Get the dashboard overview for the active account (for the auth token that initialized this client). |
| [getSingleNodeDashboard](get-single-node-dashboard.md) | [common]<br>suspend fun [getSingleNodeDashboard](get-single-node-dashboard.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)): [WalletDashboardData](../../com.lightspark.sdk.model/-wallet-dashboard-data/index.md)?<br>Get the dashboard overview for a single node as a lightning wallet. Includes balance info and the most recent transactions. |
| [getUnlockedNodeIds](get-unlocked-node-ids.md) | [common]<br>fun [getUnlockedNodeIds](get-unlocked-node-ids.md)(): Flow&lt;[Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |
| [payInvoice](pay-invoice.md) | [common]<br>suspend fun [payInvoice](pay-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60, amount: CurrencyAmountInput? = null, maxFees: CurrencyAmountInput? = null): PayInvoiceMutation.Payment<br>Pay a lightning invoice for the given node. |
| [recoverNodeSigningKey](recover-node-signing-key.md) | [common]<br>suspend fun [recoverNodeSigningKey](recover-node-signing-key.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), nodePassword: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Unlocks a node for use with the SDK for the current application session. This function must be called before any other functions that require node signing keys, including [payInvoice](pay-invoice.md)... |
