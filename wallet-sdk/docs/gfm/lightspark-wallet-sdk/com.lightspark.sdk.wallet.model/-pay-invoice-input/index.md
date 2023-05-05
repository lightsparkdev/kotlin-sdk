//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[PayInvoiceInput](index.md)

# PayInvoiceInput

[common]\
@Serializable

data class [PayInvoiceInput](index.md)(val encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val maximumFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null)

#### Parameters

common

| | |
|---|---|
| encodedInvoice | The invoice you want to pay (as defined by the BOLT11 standard). |
| timeoutSecs | The timeout in seconds that we will try to make the payment. |
| maximumFeesMsats | The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats. |
| amountMsats | The amount you will pay for this invoice, expressed in msats. It should ONLY be set when the invoice amount is zero. |

## Constructors

| | |
|---|---|
| [PayInvoiceInput](-pay-invoice-input.md) | [common]<br>fun [PayInvoiceInput](-pay-invoice-input.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), maximumFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [encodedInvoice](encoded-invoice.md) | [common]<br>val [encodedInvoice](encoded-invoice.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [maximumFeesMsats](maximum-fees-msats.md) | [common]<br>val [maximumFeesMsats](maximum-fees-msats.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [timeoutSecs](timeout-secs.md) | [common]<br>val [timeoutSecs](timeout-secs.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
