//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.auth](../index.md)/[AuthProvider](index.md)

# AuthProvider

[common]\
interface [AuthProvider](index.md)

## Functions

| Name | Summary |
|---|---|
| [getCredentialHeaders](get-credential-headers.md) | [common]<br>abstract suspend fun [getCredentialHeaders](get-credential-headers.md)(): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [isAccountAuthorized](is-account-authorized.md) | [common]<br>abstract fun [isAccountAuthorized](is-account-authorized.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [withValidAuthToken](with-valid-auth-token.md) | [common]<br>abstract fun [withValidAuthToken](with-valid-auth-token.md)(block: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |

## Inheritors

| Name |
|---|
| [OAuthProvider](../-o-auth-provider/index.md) |
| [AccountApiTokenAuthProvider](../-account-api-token-auth-provider/index.md) |
| [StubAuthProvider](../-stub-auth-provider/index.md) |
