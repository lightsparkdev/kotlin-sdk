//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkWalletClient](index.md)/[createInvoice](create-invoice.md)

# createInvoice

[common]\
suspend fun [createInvoice](create-invoice.md)(amountMsats: Long, memo: String? = null): [InvoiceData](../../com.lightspark.sdk.server.model/-invoice-data/index.md)

Creates a lightning invoice for a payment to this wallet.

#### Parameters

common

| | |
|---|---|
| amountMsats | The amount of the invoice in milli-satoshis. |
| memo | Optional memo to include in the invoice. |

#### Throws

| | |
|---|---|
| LightsparkException | If the wallet ID is not set yet. |
