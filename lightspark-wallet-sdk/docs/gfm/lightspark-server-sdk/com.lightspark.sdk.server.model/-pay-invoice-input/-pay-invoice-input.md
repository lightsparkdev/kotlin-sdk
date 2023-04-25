//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[PayInvoiceInput](index.md)/[PayInvoiceInput](-pay-invoice-input.md)

# PayInvoiceInput

[common]\
fun [PayInvoiceInput](-pay-invoice-input.md)(nodeId: String, encodedInvoice: String, timeoutSecs: Int, maximumFeesMsats: Long, amountMsats: Long? = null)

#### Parameters

common

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| encodedInvoice | The invoice you want to pay (as defined by the BOLT11 standard). |
| timeoutSecs | The timeout in seconds that we will try to make the payment. |
| maximumFeesMsats | The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats. |
| amountMsats | The amount you will pay for this invoice, expressed in msats. It should ONLY be set when the invoice amount is zero. |
