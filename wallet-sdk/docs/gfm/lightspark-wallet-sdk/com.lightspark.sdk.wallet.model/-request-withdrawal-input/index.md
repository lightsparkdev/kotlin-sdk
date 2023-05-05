//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[RequestWithdrawalInput](index.md)

# RequestWithdrawalInput

[common]\
@Serializable

data class [RequestWithdrawalInput](index.md)(val bitcoinAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amountSats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))

#### Parameters

common

| | |
|---|---|
| bitcoinAddress | The bitcoin address where the withdrawal should be sent. |
| amountSats | The amount you want to withdraw from this node in Satoshis. Use the special value -1 to withdrawal all funds from this node. |

## Constructors

| | |
|---|---|
| [RequestWithdrawalInput](-request-withdrawal-input.md) | [common]<br>fun [RequestWithdrawalInput](-request-withdrawal-input.md)(bitcoinAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountSats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountSats](amount-sats.md) | [common]<br>val [amountSats](amount-sats.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [bitcoinAddress](bitcoin-address.md) | [common]<br>val [bitcoinAddress](bitcoin-address.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
