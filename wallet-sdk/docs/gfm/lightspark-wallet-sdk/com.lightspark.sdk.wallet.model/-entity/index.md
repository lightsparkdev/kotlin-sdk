//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[Entity](index.md)

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
| [Deposit](../-deposit/index.md) |
| [IncomingPayment](../-incoming-payment/index.md) |
| [Invoice](../-invoice/index.md) |
| [LightningTransaction](../-lightning-transaction/index.md) |
| [OnChainTransaction](../-on-chain-transaction/index.md) |
| [OutgoingPayment](../-outgoing-payment/index.md) |
| [PaymentRequest](../-payment-request/index.md) |
| [Transaction](../-transaction/index.md) |
| [Wallet](../-wallet/index.md) |
| [Withdrawal](../-withdrawal/index.md) |
| [WithdrawalRequest](../-withdrawal-request/index.md) |
