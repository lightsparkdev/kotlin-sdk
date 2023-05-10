//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[IncomingPayment](index.md)/[IncomingPayment](-incoming-payment.md)

# IncomingPayment

[common]\
fun [IncomingPayment](-incoming-payment.md)(id: String, createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), destinationId: [EntityId](../-entity-id/index.md), resolvedAt: Instant? = null, transactionHash: String? = null, originId: [EntityId](../-entity-id/index.md)? = null, paymentRequestId: [EntityId](../-entity-id/index.md)? = null)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when this transaction was initiated. |
| updatedAt | The date and time when the entity was last updated. |
| status | The current status of this transaction. |
| amount | The amount of money involved in this transaction. |
| destinationId | The recipient Lightspark node this payment was sent to. |
| resolvedAt | The date and time when this transaction was completed or failed. |
| transactionHash | The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| originId | If known, the Lightspark node this payment originated from. |
| paymentRequestId | The optional payment request for this incoming payment, which will be null if the payment is sent through keysend. |