//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkException](index.md)

# LightsparkException

[common]\
open class [LightsparkException](index.md)(val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val errorCode: [LightsparkErrorCode](../-lightspark-error-code/index.md)) : [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)

A generic exception thrown by the Lightspark SDK.

## Constructors

| | |
|---|---|
| [LightsparkException](-lightspark-exception.md) | [common]<br>fun [LightsparkException](-lightspark-exception.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), errorCode: [LightsparkErrorCode](../-lightspark-error-code/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [cause](index.md#-654012527%2FProperties%2F-962664521) | [common]<br>open val [cause](index.md#-654012527%2FProperties%2F-962664521): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? |
| [errorCode](error-code.md) | [common]<br>val [errorCode](error-code.md): [LightsparkErrorCode](../-lightspark-error-code/index.md) |
| [message](message.md) | [common]<br>open override val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Inheritors

| Name |
|---|
| [MissingKeyException](../../com.lightspark.sdk.crypto/-missing-key-exception/index.md) |
| [UnsupportedCipherVersionException](../../com.lightspark.sdk.crypto/-unsupported-cipher-version-exception/index.md) |
