//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[Wallet](index.md)/[Wallet](-wallet.md)

# Wallet

[common]\
fun [Wallet](-wallet.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [WalletStatus](../-wallet-status/index.md), balances: [Balances](../-balances/index.md)? = null)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| status | The status of this wallet. |
| balances | The balances that describe the funds in this wallet. |
