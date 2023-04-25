//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[LightningFeeEstimateForInvoiceInput](index.md)/[LightningFeeEstimateForInvoiceInput](-lightning-fee-estimate-for-invoice-input.md)

# LightningFeeEstimateForInvoiceInput

[common]\
fun [LightningFeeEstimateForInvoiceInput](-lightning-fee-estimate-for-invoice-input.md)(nodeId: String, encodedPaymentRequest: String, amountMsats: Long? = null)

#### Parameters

common

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| encodedPaymentRequest | The invoice you want to pay (as defined by the BOLT11 standard). |
| amountMsats | If the invoice does not specify a payment amount, then the amount that you wish to pay, expressed in msats. |
