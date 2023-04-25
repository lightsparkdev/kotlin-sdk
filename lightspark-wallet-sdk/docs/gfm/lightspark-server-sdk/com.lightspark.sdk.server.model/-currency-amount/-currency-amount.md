//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[CurrencyAmount](index.md)/[CurrencyAmount](-currency-amount.md)

# CurrencyAmount

[common]\
fun [CurrencyAmount](-currency-amount.md)(originalValue: Long, originalUnit: [CurrencyUnit](../-currency-unit/index.md), preferredCurrencyUnit: [CurrencyUnit](../-currency-unit/index.md), preferredCurrencyValueRounded: Long, preferredCurrencyValueApprox: Float)

#### Parameters

common

| | |
|---|---|
| originalValue | The original numeric value for this CurrencyAmount. |
| originalUnit | The original unit of currency for this CurrencyAmount. |
| preferredCurrencyUnit | The unit of user's preferred currency. |
| preferredCurrencyValueRounded | The rounded numeric value for this CurrencyAmount in the very base level of user's preferred currency. For example, for USD, the value will be in cents. |
| preferredCurrencyValueApprox | The approximate float value for this CurrencyAmount in the very base level of user's preferred currency. For example, for USD, the value will be in cents. |
