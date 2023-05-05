//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkFuturesClient](index.md)/[requestWithdrawal](request-withdrawal.md)

# requestWithdrawal

[commonJvmAndroid]\
fun [requestWithdrawal](request-withdrawal.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountSats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), bitcoinAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), mode: WithdrawalMode): &lt;Error class: unknown class&gt;&lt;WithdrawalRequest&gt;

Withdraws funds from the account and sends it to the requested bitcoin address.

Depending on the chosen mode, it will first take the funds from the wallet, and if applicable, close channels appropriately to recover enough funds and reopen channels with the remaining funds. The process is asynchronous and may take up to a few minutes. You can check the progress by polling the `WithdrawalRequest` that is created, or by subscribing to a webhook.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| nodeId | The ID of the node to withdraw funds from. |
| amountSats | The amount of funds to withdraw in SATOSHI. |
| bitcoinAddress | The Bitcoin address to withdraw funds to. |
| mode | The mode to use for the withdrawal. See `WithdrawalMode` for more information. |
