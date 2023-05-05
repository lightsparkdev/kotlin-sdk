//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[TransactionFailures](index.md)

# TransactionFailures

[common]\
@Serializable

data class [TransactionFailures](index.md)(val paymentFailures: List&lt;[PaymentFailureReason](../-payment-failure-reason/index.md)&gt;? = null, val routingTransactionFailures: List&lt;[RoutingTransactionFailureReason](../-routing-transaction-failure-reason/index.md)&gt;? = null)

## Constructors

| | |
|---|---|
| [TransactionFailures](-transaction-failures.md) | [common]<br>fun [TransactionFailures](-transaction-failures.md)(paymentFailures: List&lt;[PaymentFailureReason](../-payment-failure-reason/index.md)&gt;? = null, routingTransactionFailures: List&lt;[RoutingTransactionFailureReason](../-routing-transaction-failure-reason/index.md)&gt;? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [paymentFailures](payment-failures.md) | [common]<br>val [paymentFailures](payment-failures.md): List&lt;[PaymentFailureReason](../-payment-failure-reason/index.md)&gt;? = null |
| [routingTransactionFailures](routing-transaction-failures.md) | [common]<br>val [routingTransactionFailures](routing-transaction-failures.md): List&lt;[RoutingTransactionFailureReason](../-routing-transaction-failure-reason/index.md)&gt;? = null |
