//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkFuturesClient](index.md)/[createInvoice](create-invoice.md)

# createInvoice

[commonJvmAndroid]\
fun [createInvoice](create-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, type: InvoiceType = InvoiceType.STANDARD): &lt;Error class: unknown class&gt;&lt;InvoiceData&gt;

Creates a lightning invoice for the given node.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| nodeId | The ID of the node for which to create the invoice. |
| amountMsats | The amount of the invoice in milli-satoshis. |
| memo | Optional memo to include in the invoice. |
| type | The type of invoice to create. Defaults to InvoiceType.STANDARD. |
