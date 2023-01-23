//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)/[createInvoice](create-invoice.md)

# createInvoice

[common]\
suspend fun [createInvoice](create-invoice.md)(amount: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): CreateInvoiceMutation.Invoice

Creates a lightning invoice for a payment to this wallet.

#### Parameters

common

| | |
|---|---|
| amount | The amount of the invoice in a specified currency unit. |
| memo | Optional memo to include in the invoice. |

#### Throws

| | |
|---|---|
| [LightsparkException](../-lightspark-exception/index.md) | If the wallet ID is not set yet. |
