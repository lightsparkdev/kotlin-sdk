//[shared](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkClient](index.md)/[createInvoice](create-invoice.md)

# createInvoice

[common]\
suspend fun [createInvoice](create-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amount: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): CreateInvoiceMutation.Invoice

Creates a lightning invoice for the given node.

#### Parameters

common

| | |
|---|---|
| nodeId | The ID of the node for which to create the invoice. |
| amount | The amount of the invoice in a specified currency unit. |
| memo | Optional memo to include in the invoice. |
