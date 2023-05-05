//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkWalletClient](index.md)

# LightsparkWalletClient

[common]\
class [LightsparkWalletClient](index.md)(coroutinesClient: [LightsparkCoroutinesClient](../-lightspark-coroutines-client/index.md))

Main entry point for the Lightspark Wallet SDK.

Should be constructed using the ClientConfig constructor:

```kotlin
// Initialize the client with account token info:
val lightsparkClient = LightsparkWalletClient(ClientConfig(
    authProvider = AccountApiTokenAuthProvider(
        tokenId = "your-token-id"
        token = "your-secret-token"
    )
))

// An example API call fetching the dashboard info for the active account:
val dashboard = lightsparkWalletClient.getWalletDashboard()
```

Note: This client object keeps a local cache in-memory, so a single instance should be reused throughout the lifetime of your app.

## Constructors

| | |
|---|---|
| [LightsparkWalletClient](-lightspark-wallet-client.md) | [common]<br>fun [LightsparkWalletClient](-lightspark-wallet-client.md)(config: [ClientConfig](../-client-config/index.md)) |
| [LightsparkWalletClient](-lightspark-wallet-client.md) | [common]<br>fun [LightsparkWalletClient](-lightspark-wallet-client.md)(coroutinesClient: [LightsparkCoroutinesClient](../-lightspark-coroutines-client/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [createInvoice](create-invoice.md) | [common]<br>suspend fun [createInvoice](create-invoice.md)(amountMsats: Long, memo: String? = null): [InvoiceData](../../com.lightspark.sdk.server.model/-invoice-data/index.md)<br>Creates a lightning invoice for a payment to this wallet. |
| [decodeInvoice](decode-invoice.md) | [common]<br>suspend fun [decodeInvoice](decode-invoice.md)(encodedInvoice: String): [InvoiceData](../../com.lightspark.sdk.server.model/-invoice-data/index.md)?<br>Decode a lightning invoice to get its details included payment amount, destination, etc. |
| [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md) | [common]<br>suspend fun [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)(): [FeeEstimate](../../com.lightspark.sdk.server.model/-fee-estimate/index.md)<br>Get the L1 fee estimate for a deposit or withdrawal. |
| [getWalletDashboard](get-wallet-dashboard.md) | [common]<br>suspend fun [getWalletDashboard](get-wallet-dashboard.md)(numTransactions: Int = 20): [WalletDashboard](../../com.lightspark.sdk.server.graphql/-wallet-dashboard/index.md)?<br>Get the dashboard overview for the active lightning wallet. Includes balance info and the most recent transactions. |
| [isWalletUnlocked](is-wallet-unlocked.md) | [common]<br>fun [isWalletUnlocked](is-wallet-unlocked.md)(): Boolean |
| [lockWallet](lock-wallet.md) | [common]<br>fun [lockWallet](lock-wallet.md)()<br>Locks the active wallet if needed to prevent payment until the password is entered again and passed to [unlockWallet](unlock-wallet.md). |
| [observeWalletUnlocked](observe-wallet-unlocked.md) | [common]<br>fun [observeWalletUnlocked](observe-wallet-unlocked.md)(): Flow&lt;Boolean&gt; |
| [payInvoice](pay-invoice.md) | [common]<br>suspend fun [payInvoice](pay-invoice.md)(encodedInvoice: String, maxFeesMsats: Long, amountMsats: Long? = null, timeoutSecs: Int = 60): [OutgoingPayment](../../com.lightspark.sdk.server.model/-outgoing-payment/index.md)<br>Pay a lightning invoice from this wallet. |
| [setActiveWalletWithoutUnlocking](set-active-wallet-without-unlocking.md) | [common]<br>fun [setActiveWalletWithoutUnlocking](set-active-wallet-without-unlocking.md)(walletId: String?)<br>Sets the active wallet, but does not attempt to unlock it. If the wallet is not unlocked, sensitive operations like [payInvoice](pay-invoice.md) will fail. |
| [unlockActiveWallet](unlock-active-wallet.md) | [common]<br>suspend fun [unlockActiveWallet](unlock-active-wallet.md)(password: String): Boolean<br>Unlocks the active wallet for use with sensitive SDK operations. This function or [unlockWallet](unlock-wallet.md) must be called before calling sensitive operations like [payInvoice](pay-invoice.md). |
| [unlockWallet](unlock-wallet.md) | [common]<br>suspend fun [unlockWallet](unlock-wallet.md)(walletId: String, password: String): Boolean<br>Unlocks the wallet for use with sensitive SDK operations. Also sets the active wallet to the specified walletId. This function must be called before calling any other functions that operate on the wallet. |

## Properties

| Name | Summary |
|---|---|
| [activeWalletId](active-wallet-id.md) | [common]<br>var [activeWalletId](active-wallet-id.md): String? = null |
