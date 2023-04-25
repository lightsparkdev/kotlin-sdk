//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkSyncClient](index.md)/[createInvoice](create-invoice.md)

# createInvoice

[common]\

@JvmOverloads

fun [createInvoice](create-invoice.md)(nodeId: String, amountMsats: Long, memo: String? = null, type: [InvoiceType](../../com.lightspark.sdk.server.model/-invoice-type/index.md) = InvoiceType.STANDARD): [InvoiceData](../../com.lightspark.sdk.server.model/-invoice-data/index.md)

Creates a lightning invoice for the given node.

#### Parameters

common

| | |
|---|---|
| nodeId | The ID of the node for which to create the invoice. |
| amountMsats | The amount of the invoice in milli-satoshis. |
| memo | Optional memo to include in the invoice. |
| type | The type of invoice to create. Defaults to [InvoiceType.STANDARD](../../com.lightspark.sdk.server.model/-invoice-type/-s-t-a-n-d-a-r-d/index.md). |
