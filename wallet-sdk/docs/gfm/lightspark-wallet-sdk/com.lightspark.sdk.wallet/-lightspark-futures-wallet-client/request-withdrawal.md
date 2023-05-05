//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkFuturesWalletClient](index.md)/[requestWithdrawal](request-withdrawal.md)

# requestWithdrawal

[commonJvmAndroid]\
fun [requestWithdrawal](request-withdrawal.md)(amountSats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), bitcoinAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;WithdrawalRequest&gt;

Withdraws funds from the account and sends it to the requested bitcoin address.

The process is asynchronous and may take up to a few minutes. You can check the progress by polling the `WithdrawalRequest` that is created, or by subscribing to a webhook.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| amountSats | The amount of funds to withdraw in SATOSHI. |
| bitcoinAddress | The Bitcoin address to withdraw funds to. |

#### Throws

| | |
|---|---|
| LightsparkException | if the withdrawal failed or if the wallet is locked. |
