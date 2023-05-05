//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkException](index.md)

# LightsparkException

[common]\
open class [LightsparkException](index.md)(val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val errorCode: [LightsparkErrorCode](../-lightspark-error-code/index.md), val cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null) : [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)

A generic exception thrown by the Lightspark SDK.

## Constructors

| | |
|---|---|
| [LightsparkException](-lightspark-exception.md) | [common]<br>fun [LightsparkException](-lightspark-exception.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), errorCode: [LightsparkErrorCode](../-lightspark-error-code/index.md), cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [cause](cause.md) | [common]<br>open override val [cause](cause.md): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null |
| [errorCode](error-code.md) | [common]<br>val [errorCode](error-code.md): [LightsparkErrorCode](../-lightspark-error-code/index.md) |
| [message](message.md) | [common]<br>open override val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Inheritors

| Name |
|---|
| [LightsparkAuthenticationException](../../com.lightspark.sdk.auth/-lightspark-authentication-exception/index.md) |
| [MissingKeyException](../../com.lightspark.sdk.crypto/-missing-key-exception/index.md) |
| [UnsupportedCipherVersionException](../../com.lightspark.sdk.crypto/-unsupported-cipher-version-exception/index.md) |
