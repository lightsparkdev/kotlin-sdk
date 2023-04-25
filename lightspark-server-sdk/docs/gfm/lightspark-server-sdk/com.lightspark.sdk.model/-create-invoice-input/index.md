//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[CreateInvoiceInput](index.md)

# CreateInvoiceInput

[common]\
@Serializable

data class [CreateInvoiceInput](index.md)(val nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val invoiceType: [InvoiceType](../-invoice-type/index.md)? = null)

## Constructors

| | |
|---|---|
| [CreateInvoiceInput](-create-invoice-input.md) | [common]<br>fun [CreateInvoiceInput](-create-invoice-input.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, invoiceType: [InvoiceType](../-invoice-type/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [invoiceType](invoice-type.md) | [common]<br>val [invoiceType](invoice-type.md): [InvoiceType](../-invoice-type/index.md)? = null |
| [memo](memo.md) | [common]<br>val [memo](memo.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [nodeId](node-id.md) | [common]<br>val [nodeId](node-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
