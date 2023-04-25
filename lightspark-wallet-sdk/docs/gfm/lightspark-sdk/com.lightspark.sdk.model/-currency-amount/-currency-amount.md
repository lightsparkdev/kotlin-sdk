//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[CurrencyAmount](index.md)/[CurrencyAmount](-currency-amount.md)

# CurrencyAmount

[common]\
fun [CurrencyAmount](-currency-amount.md)(originalValue: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), originalUnit: [CurrencyUnit](../-currency-unit/index.md), preferredCurrencyUnit: [CurrencyUnit](../-currency-unit/index.md), preferredCurrencyValueRounded: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), preferredCurrencyValueApprox: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html))

#### Parameters

common

| | |
|---|---|
| originalValue | The original numeric value for this CurrencyAmount. |
| originalUnit | The original unit of currency for this CurrencyAmount. |
| preferredCurrencyUnit | The unit of user's preferred currency. |
| preferredCurrencyValueRounded | The rounded numeric value for this CurrencyAmount in the very base level of user's preferred currency. For example, for USD, the value will be in cents. |
| preferredCurrencyValueApprox | The approximate float value for this CurrencyAmount in the very base level of user's preferred currency. For example, for USD, the value will be in cents. |
