//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkSyncWalletClient](index.md)/[createInvoice](create-invoice.md)

# createInvoice

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [createInvoice](create-invoice.md)(amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, type: [InvoiceType](../../com.lightspark.sdk.wallet.model/-invoice-type/index.md) = InvoiceType.STANDARD): [InvoiceData](../../com.lightspark.sdk.wallet.model/-invoice-data/index.md)

Creates a lightning invoice for the current wallet.

#### Return

The invoice data.

#### Parameters

common

| | |
|---|---|
| amountMsats | The amount of the invoice in milli-satoshis. |
| memo | Optional memo to include in the invoice. |
| type | The type of invoice to create. Defaults to [InvoiceType.STANDARD](../../com.lightspark.sdk.wallet.model/-invoice-type/-s-t-a-n-d-a-r-d/index.md). |

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | If the user is not authenticated. |