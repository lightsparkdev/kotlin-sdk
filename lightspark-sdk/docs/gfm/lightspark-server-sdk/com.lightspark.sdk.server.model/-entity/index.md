//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Entity](index.md)

# Entity

[common]\
interface [Entity](index.md)

This interface is used by all the entities in the Lightspark systems. It defines a few core fields that are available everywhere. Any object that implements this interface can be queried using the `entity` query and its ID.

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [createdAt](created-at.md) | [common]<br>abstract val [createdAt](created-at.md): Instant<br>The date and time when the entity was first created. |
| [id](id.md) | [common]<br>abstract val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| [updatedAt](updated-at.md) | [common]<br>abstract val [updatedAt](updated-at.md): Instant<br>The date and time when the entity was last updated. |

## Inheritors

| Name |
|---|
| [Account](../-account/index.md) |
| [ApiToken](../-api-token/index.md) |
| [Channel](../-channel/index.md) |
| [ChannelClosingTransaction](../-channel-closing-transaction/index.md) |
| [ChannelOpeningTransaction](../-channel-opening-transaction/index.md) |
| [Deposit](../-deposit/index.md) |
| [GraphNode](../-graph-node/index.md) |
| [Hop](../-hop/index.md) |
| [IncomingPayment](../-incoming-payment/index.md) |
| [IncomingPaymentAttempt](../-incoming-payment-attempt/index.md) |
| [Invoice](../-invoice/index.md) |
| [LightningTransaction](../-lightning-transaction/index.md) |
| [LightsparkNode](../-lightspark-node/index.md) |
| [Node](../-node/index.md) |
| [OnChainTransaction](../-on-chain-transaction/index.md) |
| [OutgoingPayment](../-outgoing-payment/index.md) |
| [OutgoingPaymentAttempt](../-outgoing-payment-attempt/index.md) |
| [PaymentRequest](../-payment-request/index.md) |
| [RoutingTransaction](../-routing-transaction/index.md) |
| [Transaction](../-transaction/index.md) |
| [Withdrawal](../-withdrawal/index.md) |
| [WithdrawalRequest](../-withdrawal-request/index.md) |
