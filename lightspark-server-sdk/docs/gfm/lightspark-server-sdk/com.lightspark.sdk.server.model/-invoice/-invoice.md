//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Invoice](index.md)/[Invoice](-invoice.md)

# Invoice

[common]\
fun [Invoice](-invoice.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, data: [InvoiceData](../-invoice-data/index.md), status: [PaymentRequestStatus](../-payment-request-status/index.md), amountPaid: [CurrencyAmount](../-currency-amount/index.md)? = null)

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
