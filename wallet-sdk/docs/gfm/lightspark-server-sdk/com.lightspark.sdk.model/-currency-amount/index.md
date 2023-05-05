//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[CurrencyAmount](index.md)

# CurrencyAmount

[common]\
@Serializable

data class [CurrencyAmount](index.md)(val originalValue: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val originalUnit: [CurrencyUnit](../-currency-unit/index.md), val preferredCurrencyUnit: [CurrencyUnit](../-currency-unit/index.md), val preferredCurrencyValueRounded: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val preferredCurrencyValueApprox: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html))

Represents the value and unit for an amount of currency.

#### Parameters

common

| | |
|---|---|
| originalValue | The original numeric value for this CurrencyAmount. |
| originalUnit | The original unit of currency for this CurrencyAmount. |
| preferredCurrencyUnit | The unit of user's preferred currency. |
| preferredCurrencyValueRounded | The rounded numeric value for this CurrencyAmount in the very base level of user's preferred currency. For example, for USD, the value will be in cents. |
| preferredCurrencyValueApprox | The approximate float value for this CurrencyAmount in the very base level of user's preferred currency. For example, for USD, the value will be in cents. |

## Constructors

| | |
|---|---|
| [CurrencyAmount](-currency-amount.md) | [common]<br>fun [CurrencyAmount](-currency-amount.md)(originalValue: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), originalUnit: [CurrencyUnit](../-currency-unit/index.md), preferredCurrencyUnit: [CurrencyUnit](../-currency-unit/index.md), preferredCurrencyValueRounded: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), preferredCurrencyValueApprox: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [originalUnit](original-unit.md) | [common]<br>val [originalUnit](original-unit.md): [CurrencyUnit](../-currency-unit/index.md) |
| [originalValue](original-value.md) | [common]<br>val [originalValue](original-value.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [preferredCurrencyUnit](preferred-currency-unit.md) | [common]<br>val [preferredCurrencyUnit](preferred-currency-unit.md): [CurrencyUnit](../-currency-unit/index.md) |
| [preferredCurrencyValueApprox](preferred-currency-value-approx.md) | [common]<br>val [preferredCurrencyValueApprox](preferred-currency-value-approx.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [preferredCurrencyValueRounded](preferred-currency-value-rounded.md) | [common]<br>val [preferredCurrencyValueRounded](preferred-currency-value-rounded.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
