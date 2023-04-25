//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[LightningFeeEstimateForInvoiceInput](index.md)/[LightningFeeEstimateForInvoiceInput](-lightning-fee-estimate-for-invoice-input.md)

# LightningFeeEstimateForInvoiceInput

[common]\
fun [LightningFeeEstimateForInvoiceInput](-lightning-fee-estimate-for-invoice-input.md)(encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null)

#### Parameters

common

| | |
|---|---|
| encodedPaymentRequest | The invoice you want to pay (as defined by the BOLT11 standard). |
| amountMsats | If the invoice does not specify a payment amount, then the amount that you wish to pay, expressed in msats. |
