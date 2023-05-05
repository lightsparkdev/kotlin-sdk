//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core.auth](../index.md)/[StubAuthProvider](index.md)

# StubAuthProvider

[common]\
class [StubAuthProvider](index.md) : [AuthProvider](../-auth-provider/index.md)

A stub implementation of [AuthProvider](../-auth-provider/index.md) that does not provide any authentication. It always stays unauthorized.

## Constructors

| | |
|---|---|
| [StubAuthProvider](-stub-auth-provider.md) | [common]<br>fun [StubAuthProvider](-stub-auth-provider.md)() |

## Functions

| Name | Summary |
|---|---|
| [getCredentialHeaders](get-credential-headers.md) | [common]<br>open suspend override fun [getCredentialHeaders](get-credential-headers.md)(): Map&lt;String, String&gt; |
| [isAccountAuthorized](is-account-authorized.md) | [common]<br>open override fun [isAccountAuthorized](is-account-authorized.md)(): Boolean |
| [withValidAuthToken](with-valid-auth-token.md) | [common]<br>open override fun [withValidAuthToken](with-valid-auth-token.md)(block: (String) -&gt; Unit) |
