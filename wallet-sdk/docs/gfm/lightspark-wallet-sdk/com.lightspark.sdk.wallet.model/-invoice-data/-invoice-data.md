//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[InvoiceData](index.md)/[InvoiceData](-invoice-data.md)

# InvoiceData

[common]\
fun [InvoiceData](-invoice-data.md)(encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), paymentHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amount: [CurrencyAmount](../-currency-amount/index.md), createdAt: Instant, expiresAt: Instant, memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

#### Parameters

common

| | |
|---|---|
| paymentHash | The payment hash of this invoice. |
| amount | The requested amount in this invoice. If it is equal to 0, the sender should choose the amount to send. |
| createdAt | The date and time when this invoice was created. |
| expiresAt | The date and time when this invoice will expire. |
| memo | A short, UTF-8 encoded, description of the purpose of this invoice. |
