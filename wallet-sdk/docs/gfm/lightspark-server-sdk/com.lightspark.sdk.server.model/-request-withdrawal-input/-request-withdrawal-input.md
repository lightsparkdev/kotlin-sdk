//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[RequestWithdrawalInput](index.md)/[RequestWithdrawalInput](-request-withdrawal-input.md)

# RequestWithdrawalInput

[common]\
fun [RequestWithdrawalInput](-request-withdrawal-input.md)(nodeId: String, bitcoinAddress: String, amountSats: Long, withdrawalMode: [WithdrawalMode](../-withdrawal-mode/index.md))

#### Parameters

common

| | |
|---|---|
| nodeId | The node from which you'd like to make the withdrawal. |
| bitcoinAddress | The bitcoin address where the withdrawal should be sent. |
| amountSats | The amount you want to withdraw from this node in Satoshis. Use the special value -1 to withdrawal all funds from this node. |
| withdrawalMode | The strategy that should be used to withdraw the funds from this node. |
