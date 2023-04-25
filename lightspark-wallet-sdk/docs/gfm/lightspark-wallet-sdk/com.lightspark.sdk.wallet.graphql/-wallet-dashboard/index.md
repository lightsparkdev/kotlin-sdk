//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.graphql](../index.md)/[WalletDashboard](index.md)

# WalletDashboard

[common]\
@Serializable

data class [WalletDashboard](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val status: [WalletStatus](../../com.lightspark.sdk.wallet.model/-wallet-status/index.md), val balances: [Balances](../../com.lightspark.sdk.wallet.model/-balances/index.md)?, val recentTransactions: [WalletToTransactionsConnection](../../com.lightspark.sdk.wallet.model/-wallet-to-transactions-connection/index.md), val paymentRequests: [WalletToPaymentRequestsConnection](../../com.lightspark.sdk.wallet.model/-wallet-to-payment-requests-connection/index.md))

## Constructors

| | |
|---|---|
| [WalletDashboard](-wallet-dashboard.md) | [common]<br>fun [WalletDashboard](-wallet-dashboard.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), status: [WalletStatus](../../com.lightspark.sdk.wallet.model/-wallet-status/index.md), balances: [Balances](../../com.lightspark.sdk.wallet.model/-balances/index.md)?, recentTransactions: [WalletToTransactionsConnection](../../com.lightspark.sdk.wallet.model/-wallet-to-transactions-connection/index.md), paymentRequests: [WalletToPaymentRequestsConnection](../../com.lightspark.sdk.wallet.model/-wallet-to-payment-requests-connection/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [balances](balances.md) | [common]<br>val [balances](balances.md): [Balances](../../com.lightspark.sdk.wallet.model/-balances/index.md)? |
| [id](id.md) | [common]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [paymentRequests](payment-requests.md) | [common]<br>val [paymentRequests](payment-requests.md): [WalletToPaymentRequestsConnection](../../com.lightspark.sdk.wallet.model/-wallet-to-payment-requests-connection/index.md) |
| [recentTransactions](recent-transactions.md) | [common]<br>val [recentTransactions](recent-transactions.md): [WalletToTransactionsConnection](../../com.lightspark.sdk.wallet.model/-wallet-to-transactions-connection/index.md) |
| [status](status.md) | [common]<br>val [status](status.md): [WalletStatus](../../com.lightspark.sdk.wallet.model/-wallet-status/index.md) |
