//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.auth](../index.md)/[AccountApiTokenAuthProvider](index.md)

# AccountApiTokenAuthProvider

[common]\
class [AccountApiTokenAuthProvider](index.md)(tokenId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tokenSecret: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : AuthProvider

## Constructors

| | |
|---|---|
| [AccountApiTokenAuthProvider](-account-api-token-auth-provider.md) | [common]<br>fun [AccountApiTokenAuthProvider](-account-api-token-auth-provider.md)(tokenId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tokenSecret: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Functions

| Name | Summary |
|---|---|
| [getCredentialHeaders](get-credential-headers.md) | [common]<br>open suspend override fun [getCredentialHeaders](get-credential-headers.md)(): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [isAccountAuthorized](is-account-authorized.md) | [common]<br>open override fun [isAccountAuthorized](is-account-authorized.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [withValidAuthToken](with-valid-auth-token.md) | [common]<br>open override fun [withValidAuthToken](with-valid-auth-token.md)(block: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
