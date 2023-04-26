//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[WithdrawalRequest](index.md)/[WithdrawalRequest](-withdrawal-request.md)

# WithdrawalRequest

[common]\
fun [WithdrawalRequest](-withdrawal-request.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, amount: [CurrencyAmount](../-currency-amount/index.md), bitcoinAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), withdrawalMode: [WithdrawalMode](../-withdrawal-mode/index.md), status: [WithdrawalRequestStatus](../-withdrawal-request-status/index.md), completedAt: Instant? = null, withdrawalId: [EntityId](../-entity-id/index.md)? = null)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| amount | The amount of money that should be withdrawn in this request. |
| bitcoinAddress | The bitcoin address where the funds should be sent. |
| withdrawalMode | The strategy that should be used to withdraw the funds from the account. |
| status | The current status of this withdrawal request. |
| completedAt | The time at which this request was completed. |
| withdrawalId | The withdrawal transaction that has been generated by this request. |