//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.auth.oauth](../index.md)/[OAuthStateStorage](index.md)

# OAuthStateStorage

[android]\
interface [OAuthStateStorage](index.md)

## Functions

| Name | Summary |
|---|---|
| [getCurrent](get-current.md) | [android]<br>abstract fun [getCurrent](get-current.md)(): AuthState |
| [replace](replace.md) | [android]<br>abstract fun [replace](replace.md)(state: AuthState?): AuthState? |
| [updateAfterAuthorization](update-after-authorization.md) | [android]<br>abstract fun [updateAfterAuthorization](update-after-authorization.md)(response: AuthorizationResponse?, ex: AuthorizationException?): AuthState? |
| [updateAfterRegistration](update-after-registration.md) | [android]<br>abstract fun [updateAfterRegistration](update-after-registration.md)(response: RegistrationResponse?, ex: AuthorizationException?): AuthState? |
| [updateAfterTokenResponse](update-after-token-response.md) | [android]<br>abstract fun [updateAfterTokenResponse](update-after-token-response.md)(response: TokenResponse?, ex: AuthorizationException?): AuthState? |

## Inheritors

| Name |
|---|
| [DataStoreOAuthStateStorage](../-data-store-o-auth-state-storage/index.md) |
| [SharedPrefsOAuthStateStorage](../-shared-prefs-o-auth-state-storage/index.md) |
