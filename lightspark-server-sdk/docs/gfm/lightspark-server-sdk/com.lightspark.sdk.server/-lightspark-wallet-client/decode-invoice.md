//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkWalletClient](index.md)/[decodeInvoice](decode-invoice.md)

# decodeInvoice

[common]\
suspend fun [decodeInvoice](decode-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [InvoiceData](../../com.lightspark.sdk.server.model/-invoice-data/index.md)?

Decode a lightning invoice to get its details included payment amount, destination, etc.

#### Return

The decoded invoice details.

#### Parameters

common

| | |
|---|---|
| encodedInvoice | An encoded string representation of the invoice to decode. |