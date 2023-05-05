//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkWalletClient](index.md)/[payInvoice](pay-invoice.md)

# payInvoice

[common]\
suspend fun [payInvoice](pay-invoice.md)(encodedInvoice: String, maxFeesMsats: Long, amountMsats: Long? = null, timeoutSecs: Int = 60): [OutgoingPayment](../../com.lightspark.sdk.server.model/-outgoing-payment/index.md)

Pay a lightning invoice from this wallet.

Note: This call will fail if the wallet is not unlocked yet via the [unlockWallet](unlock-wallet.md) function. You must successfully unlock the node with its password before calling this function.

#### Return

The payment details.

#### Parameters

common

| | |
|---|---|
| encodedInvoice | An encoded string representation of the invoice to pay. |
| maxFeesMsats | The maximum fees to pay in milli-satoshis.     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats. |
| amountMsats | The amount to pay in milli-satoshis. Defaults to the full amount of the invoice. |
| timeoutSecs | The number of seconds to wait for the payment to complete. Defaults to 60. |

#### Throws

| | |
|---|---|
| LightsparkException | If the wallet is not unlocked yet. |
