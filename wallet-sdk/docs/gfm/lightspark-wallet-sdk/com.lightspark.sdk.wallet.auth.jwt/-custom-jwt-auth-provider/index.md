//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.auth.jwt](../index.md)/[CustomJwtAuthProvider](index.md)

# CustomJwtAuthProvider

[common]\
class [CustomJwtAuthProvider](index.md)(tokenStorage: [JwtStorage](../-jwt-storage/index.md)) : AuthProvider

A custom AuthProvider that uses a JWT token to authenticate requests.

Should generally not be used directly by clients, but rather through the loginWithJwt method of a LightsparkWalletClient.

#### Parameters

common

| | |
|---|---|
| tokenStorage | A [JwtStorage](../-jwt-storage/index.md) implementation that stores or retrieves the current JWT token info. |

## Constructors

| | |
|---|---|
| [CustomJwtAuthProvider](-custom-jwt-auth-provider.md) | [common]<br>fun [CustomJwtAuthProvider](-custom-jwt-auth-provider.md)(tokenStorage: [JwtStorage](../-jwt-storage/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getCredentialHeaders](get-credential-headers.md) | [common]<br>open suspend override fun [getCredentialHeaders](get-credential-headers.md)(): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [isAccountAuthorized](is-account-authorized.md) | [common]<br>open override fun [isAccountAuthorized](is-account-authorized.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setTokenInfo](set-token-info.md) | [common]<br>fun [setTokenInfo](set-token-info.md)(tokenInfo: [JwtTokenInfo](../-jwt-token-info/index.md)?)<br>Sets the current JWT token info and saves it in the tokenStorage asynchronously. |
| [withValidAuthToken](with-valid-auth-token.md) | [common]<br>open override fun [withValidAuthToken](with-valid-auth-token.md)(block: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
