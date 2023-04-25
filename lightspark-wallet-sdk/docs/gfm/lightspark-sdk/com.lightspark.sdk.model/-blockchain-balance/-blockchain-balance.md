//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[BlockchainBalance](index.md)/[BlockchainBalance](-blockchain-balance.md)

# BlockchainBalance

[common]\
fun [BlockchainBalance](-blockchain-balance.md)(totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, confirmedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, unconfirmedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, lockedBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, requiredReserve: [CurrencyAmount](../-currency-amount/index.md)? = null, availableBalance: [CurrencyAmount](../-currency-amount/index.md)? = null)

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
