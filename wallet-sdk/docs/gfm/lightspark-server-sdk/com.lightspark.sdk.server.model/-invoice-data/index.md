//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[InvoiceData](index.md)

# InvoiceData

[common]\
@Serializable

data class [InvoiceData](index.md)(val encodedPaymentRequest: String, val bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), val paymentHash: String, val amount: [CurrencyAmount](../-currency-amount/index.md), val createdAt: Instant, val expiresAt: Instant, val destination: [Node](../-node/index.md), val memo: String? = null) : [PaymentRequestData](../-payment-request-data/index.md)

This object represents the BOLT #11 invoice protocol for Lightning Payments. See https://github.com/lightning/bolts/blob/master/11-payment-encoding.md.

## Constructors

| | |
|---|---|
| [InvoiceData](-invoice-data.md) | [common]<br>fun [InvoiceData](-invoice-data.md)(encodedPaymentRequest: String, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), paymentHash: String, amount: [CurrencyAmount](../-currency-amount/index.md), createdAt: Instant, expiresAt: Instant, destination: [Node](../-node/index.md), memo: String? = null) |

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
| [destination](destination.md) | [common]<br>val [destination](destination.md): [Node](../-node/index.md) |
| [encodedPaymentRequest](encoded-payment-request.md) | [common]<br>open override val [encodedPaymentRequest](encoded-payment-request.md): String |
| [expiresAt](expires-at.md) | [common]<br>val [expiresAt](expires-at.md): Instant |
| [memo](memo.md) | [common]<br>val [memo](memo.md): String? = null |
| [paymentHash](payment-hash.md) | [common]<br>val [paymentHash](payment-hash.md): String |
