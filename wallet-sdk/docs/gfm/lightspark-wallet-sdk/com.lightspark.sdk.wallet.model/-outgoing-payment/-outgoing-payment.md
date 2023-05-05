//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[OutgoingPayment](index.md)/[OutgoingPayment](-outgoing-payment.md)

# OutgoingPayment

[common]\
fun [OutgoingPayment](-outgoing-payment.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), resolvedAt: Instant? = null, transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null, paymentRequestData: [PaymentRequestData](../-payment-request-data/index.md)? = null, failureReason: [PaymentFailureReason](../-payment-failure-reason/index.md)? = null, failureMessage: [RichText](../-rich-text/index.md)? = null)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when this transaction was initiated. |
| updatedAt | The date and time when the entity was last updated. |
| status | The current status of this transaction. |
| amount | The amount of money involved in this transaction. |
| resolvedAt | The date and time when this transaction was completed or failed. |
| transactionHash | The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| fees | The fees paid by the sender node to send the payment. |
| paymentRequestData | The data of the payment request that was paid by this transaction, if known. |
| failureReason | If applicable, the reason why the payment failed. |
| failureMessage | If applicable, user-facing error message describing why the payment failed. |
