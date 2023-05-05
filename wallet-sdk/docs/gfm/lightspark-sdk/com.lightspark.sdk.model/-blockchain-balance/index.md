//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[BlockchainBalance](index.md)

# BlockchainBalance

[common]\
@Serializable

data class [BlockchainBalance](index.md)(val totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val confirmedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val unconfirmedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val lockedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val requiredReserve: [CurrencyAmount](../-currency-amount/index.md)? = null, val availableBalance: [CurrencyAmount](../-currency-amount/index.md)? = null)

This object provides a detailed breakdown of a `LightsparkNode`'s current balance on the Bitcoin Network.

#### Parameters

common

| | |
|---|---|
| totalBalance | The total wallet balance, including unconfirmed UTXOs. |
| confirmedBalance | The balance of confirmed UTXOs in the wallet. |
| unconfirmedBalance | The balance of unconfirmed UTXOs in the wallet. |
| lockedBalance | The balance that's locked by an on-chain transaction. |
| requiredReserve | Funds required to be held in reserve for channel bumping. |
| availableBalance | Funds available for creating channels or withdrawing. |

## Constructors

| | |
|---|---|
| [BlockchainBalance](-blockchain-balance.md) | [common]<br>fun [BlockchainBalance](-blockchain-balance.md)(totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, confirmedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, unconfirmedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, lockedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, requiredReserve: [CurrencyAmount](../-currency-amount/index.md)? = null, availableBalance: [CurrencyAmount](../-currency-amount/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [availableBalance](available-balance.md) | [common]<br>val [availableBalance](available-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [confirmedBalance](confirmed-balance.md) | [common]<br>val [confirmedBalance](confirmed-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [lockedBalance](locked-balance.md) | [common]<br>val [lockedBalance](locked-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [requiredReserve](required-reserve.md) | [common]<br>val [requiredReserve](required-reserve.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [totalBalance](total-balance.md) | [common]<br>val [totalBalance](total-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [unconfirmedBalance](unconfirmed-balance.md) | [common]<br>val [unconfirmedBalance](unconfirmed-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
