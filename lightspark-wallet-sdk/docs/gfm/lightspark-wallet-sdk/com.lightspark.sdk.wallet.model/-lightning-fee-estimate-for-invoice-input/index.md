//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[LightningFeeEstimateForInvoiceInput](index.md)

# LightningFeeEstimateForInvoiceInput

[common]\
@Serializable

data class [LightningFeeEstimateForInvoiceInput](index.md)(val encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null)

#### Parameters

common

| | |
|---|---|
| encodedPaymentRequest | The invoice you want to pay (as defined by the BOLT11 standard). |
| amountMsats | If the invoice does not specify a payment amount, then the amount that you wish to pay, expressed in msats. |

## Constructors

| | |
|---|---|
| [LightningFeeEstimateForInvoiceInput](-lightning-fee-estimate-for-invoice-input.md) | [common]<br>fun [LightningFeeEstimateForInvoiceInput](-lightning-fee-estimate-for-invoice-input.md)(encodedPaymentRequest: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null |
| [encodedPaymentRequest](encoded-payment-request.md) | [common]<br>val [encodedPaymentRequest](encoded-payment-request.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
