//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkSyncClient](index.md)/[getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md)

# getLightningFeeEstimateForInvoice

[common]\

@JvmOverloads

suspend fun [getLightningFeeEstimateForInvoice](get-lightning-fee-estimate-for-invoice.md)(nodeId: String, encodedPaymentRequest: String, amountMsats: Long? = null): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)

Gets an estimate of the fees that will be paid for a Lightning invoice.

#### Parameters

common

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| encodedPaymentRequest | The invoice you want to pay (as defined by the BOLT11 standard). |
| amountMsats | If the invoice does not specify a payment amount, then the amount that you wish to pay,     expressed in msats. |
