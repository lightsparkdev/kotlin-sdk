//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.util](../index.md)/[EnumSerializer](index.md)

# EnumSerializer

[common]\
open class [EnumSerializer](index.md)&lt;[T](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[T](index.md)&gt;&gt;(enumClass: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[T](index.md)&gt;, fromString: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [T](index.md)) : KSerializer&lt;[T](index.md)&gt;

## Constructors

| | |
|---|---|
| [EnumSerializer](-enum-serializer.md) | [common]<br>fun &lt;[T](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[T](index.md)&gt;&gt; [EnumSerializer](-enum-serializer.md)(enumClass: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[T](index.md)&gt;, fromString: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [T](index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deserialize](deserialize.md) | [common]<br>open override fun [deserialize](deserialize.md)(decoder: Decoder): [T](index.md) |
| [serialize](serialize.md) | [common]<br>open override fun [serialize](serialize.md)(encoder: Encoder, value: [T](index.md)) |

## Properties

| Name | Summary |
|---|---|
| [descriptor](descriptor.md) | [common]<br>open override val [descriptor](descriptor.md): SerialDescriptor |

## Inheritors

| Name |
|---|
| [BitcoinNetworkSerializer](../../com.lightspark.sdk.model/-bitcoin-network-serializer/index.md) |
| [ChannelStatusSerializer](../../com.lightspark.sdk.model/-channel-status-serializer/index.md) |
| [CurrencyUnitSerializer](../../com.lightspark.sdk.model/-currency-unit-serializer/index.md) |
| [HtlcAttemptFailureCodeSerializer](../../com.lightspark.sdk.model/-htlc-attempt-failure-code-serializer/index.md) |
| [IncomingPaymentAttemptStatusSerializer](../../com.lightspark.sdk.model/-incoming-payment-attempt-status-serializer/index.md) |
| [InvoiceTypeSerializer](../../com.lightspark.sdk.model/-invoice-type-serializer/index.md) |
| [LightsparkNodePurposeSerializer](../../com.lightspark.sdk.model/-lightspark-node-purpose-serializer/index.md) |
| [LightsparkNodeStatusSerializer](../../com.lightspark.sdk.model/-lightspark-node-status-serializer/index.md) |
| [NodeAddressTypeSerializer](../../com.lightspark.sdk.model/-node-address-type-serializer/index.md) |
| [OutgoingPaymentAttemptStatusSerializer](../../com.lightspark.sdk.model/-outgoing-payment-attempt-status-serializer/index.md) |
| [PaymentFailureReasonSerializer](../../com.lightspark.sdk.model/-payment-failure-reason-serializer/index.md) |
| [PaymentRequestStatusSerializer](../../com.lightspark.sdk.model/-payment-request-status-serializer/index.md) |
| [PermissionSerializer](../../com.lightspark.sdk.model/-permission-serializer/index.md) |
| [RoutingTransactionFailureReasonSerializer](../../com.lightspark.sdk.model/-routing-transaction-failure-reason-serializer/index.md) |
| [TransactionStatusSerializer](../../com.lightspark.sdk.model/-transaction-status-serializer/index.md) |
| [TransactionTypeSerializer](../../com.lightspark.sdk.model/-transaction-type-serializer/index.md) |
| [WebhookEventTypeSerializer](../../com.lightspark.sdk.model/-webhook-event-type-serializer/index.md) |
| [WithdrawalModeSerializer](../../com.lightspark.sdk.model/-withdrawal-mode-serializer/index.md) |
| [WithdrawalRequestStatusSerializer](../../com.lightspark.sdk.model/-withdrawal-request-status-serializer/index.md) |
