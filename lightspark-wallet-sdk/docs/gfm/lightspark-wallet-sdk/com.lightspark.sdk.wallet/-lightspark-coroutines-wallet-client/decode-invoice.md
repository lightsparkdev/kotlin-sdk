//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[decodeInvoice](decode-invoice.md)

# decodeInvoice

[common]\
suspend fun [decodeInvoice](decode-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [InvoiceData](../../com.lightspark.sdk.wallet.model/-invoice-data/index.md)?

Decode a lightning invoice to get its details included payment amount, destination, etc.

#### Return

The decoded invoice details.

#### Parameters

common

| | |
|---|---|
| encodedInvoice | An encoded string representation of the invoice to decode. |
