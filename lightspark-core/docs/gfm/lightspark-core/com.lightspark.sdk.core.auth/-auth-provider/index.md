//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core.auth](../index.md)/[AuthProvider](index.md)

# AuthProvider

[common]\
interface [AuthProvider](index.md)

## Functions

| Name | Summary |
|---|---|
| [getCredentialHeaders](get-credential-headers.md) | [common]<br>abstract suspend fun [getCredentialHeaders](get-credential-headers.md)(): Map&lt;String, String&gt; |
| [isAccountAuthorized](is-account-authorized.md) | [common]<br>abstract fun [isAccountAuthorized](is-account-authorized.md)(): Boolean |
| [withValidAuthToken](with-valid-auth-token.md) | [common]<br>abstract fun [withValidAuthToken](with-valid-auth-token.md)(block: (String) -&gt; Unit) |

## Inheritors

| Name |
|---|
| [StubAuthProvider](../-stub-auth-provider/index.md) |
