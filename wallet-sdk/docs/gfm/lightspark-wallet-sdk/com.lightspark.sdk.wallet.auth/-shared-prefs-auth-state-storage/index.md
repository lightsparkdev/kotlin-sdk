//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.auth](../index.md)/[SharedPrefsAuthStateStorage](index.md)

# SharedPrefsAuthStateStorage

[android]\
class [SharedPrefsAuthStateStorage](index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [AuthStateStorage](../-auth-state-storage/index.md)

## Constructors

| | |
|---|---|
| [SharedPrefsAuthStateStorage](-shared-prefs-auth-state-storage.md) | [android]<br>fun [SharedPrefsAuthStateStorage](-shared-prefs-auth-state-storage.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [android]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getCurrent](get-current.md) | [android]<br>open override fun [getCurrent](get-current.md)(): AuthState |
| [replace](replace.md) | [android]<br>open override fun [replace](replace.md)(state: AuthState?): AuthState? |
| [updateAfterAuthorization](update-after-authorization.md) | [android]<br>open override fun [updateAfterAuthorization](update-after-authorization.md)(response: AuthorizationResponse?, ex: AuthorizationException?): AuthState? |
| [updateAfterRegistration](update-after-registration.md) | [android]<br>open override fun [updateAfterRegistration](update-after-registration.md)(response: RegistrationResponse?, ex: AuthorizationException?): AuthState? |
| [updateAfterTokenResponse](update-after-token-response.md) | [android]<br>open override fun [updateAfterTokenResponse](update-after-token-response.md)(response: TokenResponse?, ex: AuthorizationException?): AuthState? |
