//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[InvoiceData](index.md)

# InvoiceData

[common]\
@Serializable

data class [InvoiceData](index.md)(val encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), val paymentHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amount: [CurrencyAmount](../-currency-amount/index.md), val createdAt: Instant, val expiresAt: Instant, val memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) : [PaymentRequestData](../-payment-request-data/index.md)

This object represents the BOLT #11 invoice protocol for Lightning Payments. See https://github.com/lightning/bolts/blob/master/11-payment-encoding.md.

#### Parameters

common

| | |
|---|---|
| paymentHash | The payment hash of this invoice. |
| amount | The requested amount in this invoice. If it is equal to 0, the sender should choose the amount to send. |
| createdAt | The date and time when this invoice was created. |
| expiresAt | The date and time when this invoice will expire. |
| memo | A short, UTF-8 encoded, description of the purpose of this invoice. |

## Constructors

| | |
|---|---|
| [InvoiceData](-invoice-data.md) | [common]<br>fun [InvoiceData](-invoice-data.md)(encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), paymentHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amount: [CurrencyAmount](../-currency-amount/index.md), createdAt: Instant, expiresAt: Instant, memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md) |
| [bitcoinNetwork](bitcoin-network.md) | [common]<br>open override val [bitcoinNetwork](bitcoin-network.md): [BitcoinNetwork](../-bitcoin-network/index.md) |
| [createdAt](created-at.md) | [common]<br>val [createdAt](created-at.md): Instant |
| [encodedPaymentRequest](encoded-payment-request.md) | [common]<br>open override val [encodedPaymentRequest](encoded-payment-request.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [expiresAt](expires-at.md) | [common]<br>val [expiresAt](expires-at.md): Instant |
| [memo](memo.md) | [common]<br>val [memo](memo.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [paymentHash](payment-hash.md) | [common]<br>val [paymentHash](payment-hash.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
