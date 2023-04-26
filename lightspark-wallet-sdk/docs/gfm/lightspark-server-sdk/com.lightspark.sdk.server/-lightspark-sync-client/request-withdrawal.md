//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkSyncClient](index.md)/[requestWithdrawal](request-withdrawal.md)

# requestWithdrawal

[common]\
fun [requestWithdrawal](request-withdrawal.md)(nodeId: String, amountSats: Long, bitcoinAddress: String, mode: [WithdrawalMode](../../com.lightspark.sdk.server.model/-withdrawal-mode/index.md)): [WithdrawalRequest](../../com.lightspark.sdk.server.model/-withdrawal-request/index.md)

Withdraws funds from the account and sends it to the requested bitcoin address.

Depending on the chosen mode, it will first take the funds from the wallet, and if applicable, close channels appropriately to recover enough funds and reopen channels with the remaining funds. The process is asynchronous and may take up to a few minutes. You can check the progress by polling the `WithdrawalRequest` that is created, or by subscribing to a webhook.

#### Parameters

common

| | |
|---|---|
| nodeId | The ID of the node to withdraw funds from. |
| amountSats | The amount of funds to withdraw in SATOSHI. |
| bitcoinAddress | The Bitcoin address to withdraw funds to. |
| mode | The mode to use for the withdrawal. See `WithdrawalMode` for more information. |