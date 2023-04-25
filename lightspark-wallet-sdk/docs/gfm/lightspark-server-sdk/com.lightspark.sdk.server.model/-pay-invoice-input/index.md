//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[PayInvoiceInput](index.md)

# PayInvoiceInput

[common]\
@Serializable

data class [PayInvoiceInput](index.md)(val nodeId: String, val encodedInvoice: String, val timeoutSecs: Int, val maximumFeesMsats: Long, val amountMsats: Long? = null)

#### Parameters

common

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| encodedInvoice | The invoice you want to pay (as defined by the BOLT11 standard). |
| timeoutSecs | The timeout in seconds that we will try to make the payment. |
| maximumFeesMsats | The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats. |
| amountMsats | The amount you will pay for this invoice, expressed in msats. It should ONLY be set when the invoice amount is zero. |

## Constructors

| | |
|---|---|
| [PayInvoiceInput](-pay-invoice-input.md) | [common]<br>fun [PayInvoiceInput](-pay-invoice-input.md)(nodeId: String, encodedInvoice: String, timeoutSecs: Int, maximumFeesMsats: Long, amountMsats: Long? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): Long? = null |
| [encodedInvoice](encoded-invoice.md) | [common]<br>val [encodedInvoice](encoded-invoice.md): String |
| [maximumFeesMsats](maximum-fees-msats.md) | [common]<br>val [maximumFeesMsats](maximum-fees-msats.md): Long |
| [nodeId](node-id.md) | [common]<br>val [nodeId](node-id.md): String |
| [timeoutSecs](timeout-secs.md) | [common]<br>val [timeoutSecs](timeout-secs.md): Int |
