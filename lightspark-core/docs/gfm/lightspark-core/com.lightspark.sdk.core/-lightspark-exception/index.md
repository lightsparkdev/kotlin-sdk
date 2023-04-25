//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core](../index.md)/[LightsparkException](index.md)

# LightsparkException

[common]\
open class [LightsparkException](index.md)(val message: String, val errorCode: [LightsparkErrorCode](../-lightspark-error-code/index.md), val cause: Throwable? = null) : Exception

A generic exception thrown by the Lightspark SDK.

## Constructors

| | |
|---|---|
| [LightsparkException](-lightspark-exception.md) | [common]<br>fun [LightsparkException](-lightspark-exception.md)(message: String, errorCode: [LightsparkErrorCode](../-lightspark-error-code/index.md), cause: Throwable? = null) |

## Properties

| Name | Summary |
|---|---|
| [cause](cause.md) | [common]<br>open override val [cause](cause.md): Throwable? = null |
| [errorCode](error-code.md) | [common]<br>val [errorCode](error-code.md): [LightsparkErrorCode](../-lightspark-error-code/index.md) |
| [message](message.md) | [common]<br>open override val [message](message.md): String |

## Inheritors

| Name |
|---|
| [MissingKeyException](../../com.lightspark.sdk.core.crypto/-missing-key-exception/index.md) |
| [UnsupportedCipherVersionException](../../com.lightspark.sdk.core.crypto/-unsupported-cipher-version-exception/index.md) |
| [LightsparkAuthenticationException](../../com.lightspark.sdk.core.auth/-lightspark-authentication-exception/index.md) |
