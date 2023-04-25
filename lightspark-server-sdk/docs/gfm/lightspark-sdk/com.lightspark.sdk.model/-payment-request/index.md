//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[PaymentRequest](index.md)

# PaymentRequest

[common]\
interface [PaymentRequest](index.md) : [Entity](../-entity/index.md)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [createdAt](created-at.md) | [common]<br>abstract override val [createdAt](created-at.md): Instant<br>The date and time when the entity was first created. |
| [data](data.md) | [common]<br>abstract val [data](data.md): [PaymentRequestData](../-payment-request-data/index.md)<br>The details of the payment request. |
| [id](id.md) | [common]<br>abstract override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| [status](status.md) | [common]<br>abstract val [status](status.md): [PaymentRequestStatus](../-payment-request-status/index.md)<br>The status of the payment request. |
| [updatedAt](updated-at.md) | [common]<br>abstract override val [updatedAt](updated-at.md): Instant<br>The date and time when the entity was last updated. |

## Inheritors

| Name |
|---|
| [Invoice](../-invoice/index.md) |
