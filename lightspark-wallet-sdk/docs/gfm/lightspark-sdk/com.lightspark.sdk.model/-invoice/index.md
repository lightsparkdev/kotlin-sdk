//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[Invoice](index.md)

# Invoice

[common]\
@Serializable

data class [Invoice](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val data: [InvoiceData](../-invoice-data/index.md), val status: [PaymentRequestStatus](../-payment-request-status/index.md), val amountPaid: [CurrencyAmount](../-currency-amount/index.md)? = null) : [PaymentRequest](../-payment-request/index.md), [Entity](../-entity/index.md)

This object represents a BOLT #11 invoice (https://github.com/lightning/bolts/blob/master/11-payment-encoding.md) initiated by a Lightspark Node.

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| data | The details of the invoice. |
| status | The status of the payment request. |
| amountPaid | The total amount that has been paid to this invoice. |

## Constructors

| | |
|---|---|
| [Invoice](-invoice.md) | [common]<br>fun [Invoice](-invoice.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, data: [InvoiceData](../-invoice-data/index.md), status: [PaymentRequestStatus](../-payment-request-status/index.md), amountPaid: [CurrencyAmount](../-currency-amount/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountPaid](amount-paid.md) | [common]<br>val [amountPaid](amount-paid.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [data](data.md) | [common]<br>open override val [data](data.md): [InvoiceData](../-invoice-data/index.md) |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [status](status.md) | [common]<br>open override val [status](status.md): [PaymentRequestStatus](../-payment-request-status/index.md) |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
