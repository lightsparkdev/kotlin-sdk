//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkFuturesClient](index.md)/[getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md)

# getLightningFeeEstimateForInvoice

[commonJvmAndroid]\
suspend fun [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;

Gets an estimate of the fees that will be paid for a Lightning invoice.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| encodedPaymentRequest | The invoice you want to pay (as defined by the BOLT11 standard). |
| amountMsats | If the invoice does not specify a payment amount, then the amount that you wish to pay,     expressed in msats. |
