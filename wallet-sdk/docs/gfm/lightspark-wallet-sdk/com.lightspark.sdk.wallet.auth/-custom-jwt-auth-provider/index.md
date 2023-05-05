//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.auth](../index.md)/[CustomJwtAuthProvider](index.md)

# CustomJwtAuthProvider

[common]\
class [CustomJwtAuthProvider](index.md)(accessToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), validUntil: Instant) : AuthProvider

A custom AuthProvider that uses a JWT token to authenticate requests.

Should generally not be used directly by clients, but rather through the loginWithJwt method of a LightsparkWalletClient.

#### Parameters

common

| | |
|---|---|
| accessToken | The JWT token to use for authentication. |
| validUntil | The time at which the token expires. |

## Constructors

| | |
|---|---|
| [CustomJwtAuthProvider](-custom-jwt-auth-provider.md) | [common]<br>fun [CustomJwtAuthProvider](-custom-jwt-auth-provider.md)(accessToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), validUntil: Instant) |

## Functions

| Name | Summary |
|---|---|
| [getCredentialHeaders](get-credential-headers.md) | [common]<br>open suspend override fun [getCredentialHeaders](get-credential-headers.md)(): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [isAccountAuthorized](is-account-authorized.md) | [common]<br>open override fun [isAccountAuthorized](is-account-authorized.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [withValidAuthToken](with-valid-auth-token.md) | [common]<br>open override fun [withValidAuthToken](with-valid-auth-token.md)(block: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
