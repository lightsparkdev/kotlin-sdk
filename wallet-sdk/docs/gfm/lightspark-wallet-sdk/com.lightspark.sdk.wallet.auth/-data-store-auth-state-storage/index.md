//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.auth](../index.md)/[DataStoreAuthStateStorage](index.md)

# DataStoreAuthStateStorage

[android]\
class [DataStoreAuthStateStorage](index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [AuthStateStorage](../-auth-state-storage/index.md)

## Constructors

| | |
|---|---|
| [DataStoreAuthStateStorage](-data-store-auth-state-storage.md) | [android]<br>fun [DataStoreAuthStateStorage](-data-store-auth-state-storage.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [android]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getCurrent](get-current.md) | [android]<br>open override fun [getCurrent](get-current.md)(): AuthState |
| [observeIsAuthorized](observe-is-authorized.md) | [android]<br>fun [observeIsAuthorized](observe-is-authorized.md)(): Flow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [replace](replace.md) | [android]<br>open override fun [replace](replace.md)(state: AuthState?): AuthState? |
| [updateAfterAuthorization](update-after-authorization.md) | [android]<br>open override fun [updateAfterAuthorization](update-after-authorization.md)(response: AuthorizationResponse?, ex: AuthorizationException?): AuthState? |
| [updateAfterRegistration](update-after-registration.md) | [android]<br>open override fun [updateAfterRegistration](update-after-registration.md)(response: RegistrationResponse?, ex: AuthorizationException?): AuthState? |
| [updateAfterTokenResponse](update-after-token-response.md) | [android]<br>open override fun [updateAfterTokenResponse](update-after-token-response.md)(response: TokenResponse?, ex: AuthorizationException?): AuthState? |
