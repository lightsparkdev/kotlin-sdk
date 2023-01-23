//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)

# LightsparkWalletClient

[common]\
class [LightsparkWalletClient](index.md)

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [common]<br>class [Builder](-builder/index.md) |

## Functions

| Name | Summary |
|---|---|
| [createInvoice](create-invoice.md) | [common]<br>suspend fun [createInvoice](create-invoice.md)(amount: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): CreateInvoiceMutation.Invoice<br>Creates a lightning invoice for a payment to this wallet. |
| [createNewWallet](create-new-wallet.md) | [common]<br>suspend fun [createNewWallet](create-new-wallet.md)(password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK))<br>Creates a new wallet for the user. This will create a new node on the server. |
| [decodeInvoice](decode-invoice.md) | [common]<br>suspend fun [decodeInvoice](decode-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): DecodedPaymentRequestQuery.OnInvoiceData?<br>Decode a lightning invoice to get its details included payment amount, destination, etc. |
| [getFeeEstimate](get-fee-estimate.md) | [common]<br>suspend fun [getFeeEstimate](get-fee-estimate.md)(bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)): [FeeEstimate](../../com.lightspark.sdk.model/-fee-estimate/index.md)<br>Get the fee estimate for a payment. |
| [getWalletDashboard](get-wallet-dashboard.md) | [common]<br>suspend fun [getWalletDashboard](get-wallet-dashboard.md)(numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)): [WalletDashboardData](../../com.lightspark.sdk.model/-wallet-dashboard-data/index.md)?<br>Get the dashboard overview for the active lightning wallet. Includes balance info and the most recent transactions. |
| [isWalletUnlocked](is-wallet-unlocked.md) | [common]<br>fun [isWalletUnlocked](is-wallet-unlocked.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [lockWallet](lock-wallet.md) | [common]<br>fun [lockWallet](lock-wallet.md)()<br>Locks the active wallet if needed to prevent payment until the password is entered again and passed to [unlockWallet](unlock-wallet.md). |
| [observeWalletUnlocked](observe-wallet-unlocked.md) | [common]<br>fun [observeWalletUnlocked](observe-wallet-unlocked.md)(): Flow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [payInvoice](pay-invoice.md) | [common]<br>suspend fun [payInvoice](pay-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60, amount: CurrencyAmountInput? = null, maxFees: CurrencyAmountInput? = null): PayInvoiceMutation.Payment<br>Pay a lightning invoice from this wallet. |
| [unlockActiveWallet](unlock-active-wallet.md) | [common]<br>suspend fun [unlockActiveWallet](unlock-active-wallet.md)(password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Unlocks the active wallet for use with sensitive SDK operations. This function or [unlockWallet](unlock-wallet.md) must be called before calling sensitive operations like [payInvoice](pay-invoice.md). |
| [unlockWallet](unlock-wallet.md) | [common]<br>suspend fun [unlockWallet](unlock-wallet.md)(walletId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Unlocks the wallet for use with sensitive SDK operations. Also sets the active wallet to the specified walletId. This function must be called before calling any other functions that operate on the wallet. |

## Properties

| Name | Summary |
|---|---|
| [activeWalletId](active-wallet-id.md) | [common]<br>var [activeWalletId](active-wallet-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
