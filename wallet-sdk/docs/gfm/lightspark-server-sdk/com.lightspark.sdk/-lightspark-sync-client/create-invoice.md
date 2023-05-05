//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkSyncClient](index.md)/[createInvoice](create-invoice.md)

# createInvoice

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [createInvoice](create-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, type: [InvoiceType](../../com.lightspark.sdk.model/-invoice-type/index.md) = InvoiceType.STANDARD): [InvoiceData](../../com.lightspark.sdk.model/-invoice-data/index.md)

Creates a lightning invoice for the given node.

#### Parameters

common

| | |
|---|---|
| nodeId | The ID of the node for which to create the invoice. |
| amountMsats | The amount of the invoice in milli-satoshis. |
| memo | Optional memo to include in the invoice. |
| type | The type of invoice to create. Defaults to [InvoiceType.STANDARD](../../com.lightspark.sdk.model/-invoice-type/-s-t-a-n-d-a-r-d/index.md). |
