//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[LightningFeeEstimateForInvoiceInput](index.md)

# LightningFeeEstimateForInvoiceInput

[common]\
@Serializable

data class [LightningFeeEstimateForInvoiceInput](index.md)(val nodeId: String, val encodedPaymentRequest: String, val amountMsats: Long? = null)

#### Parameters

common

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| encodedPaymentRequest | The invoice you want to pay (as defined by the BOLT11 standard). |
| amountMsats | If the invoice does not specify a payment amount, then the amount that you wish to pay, expressed in msats. |

## Constructors

| | |
|---|---|
| [LightningFeeEstimateForInvoiceInput](-lightning-fee-estimate-for-invoice-input.md) | [common]<br>fun [LightningFeeEstimateForInvoiceInput](-lightning-fee-estimate-for-invoice-input.md)(nodeId: String, encodedPaymentRequest: String, amountMsats: Long? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): Long? = null |
| [encodedPaymentRequest](encoded-payment-request.md) | [common]<br>val [encodedPaymentRequest](encoded-payment-request.md): String |
| [nodeId](node-id.md) | [common]<br>val [nodeId](node-id.md): String |
