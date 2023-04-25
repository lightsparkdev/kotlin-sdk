//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[CreateInvoiceInput](index.md)

# CreateInvoiceInput

[common]\
@Serializable

data class [CreateInvoiceInput](index.md)(val nodeId: String, val amountMsats: Long, val memo: String? = null, val invoiceType: [InvoiceType](../-invoice-type/index.md)? = null)

## Constructors

| | |
|---|---|
| [CreateInvoiceInput](-create-invoice-input.md) | [common]<br>fun [CreateInvoiceInput](-create-invoice-input.md)(nodeId: String, amountMsats: Long, memo: String? = null, invoiceType: [InvoiceType](../-invoice-type/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): Long |
| [invoiceType](invoice-type.md) | [common]<br>val [invoiceType](invoice-type.md): [InvoiceType](../-invoice-type/index.md)? = null |
| [memo](memo.md) | [common]<br>val [memo](memo.md): String? = null |
| [nodeId](node-id.md) | [common]<br>val [nodeId](node-id.md): String |
