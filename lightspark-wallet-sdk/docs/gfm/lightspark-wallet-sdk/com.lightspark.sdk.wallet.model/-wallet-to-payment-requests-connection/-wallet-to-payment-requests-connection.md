//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[WalletToPaymentRequestsConnection](index.md)/[WalletToPaymentRequestsConnection](-wallet-to-payment-requests-connection.md)

# WalletToPaymentRequestsConnection

[common]\
fun [WalletToPaymentRequestsConnection](-wallet-to-payment-requests-connection.md)(pageInfo: [PageInfo](../-page-info/index.md), count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[PaymentRequest](../-payment-request/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| pageInfo | An object that holds pagination information about the objects in this connection. |
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The payment requests for the current page of this connection. |
