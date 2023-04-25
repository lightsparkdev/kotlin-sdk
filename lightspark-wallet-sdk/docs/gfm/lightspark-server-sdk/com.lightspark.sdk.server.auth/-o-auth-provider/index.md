//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.auth](../index.md)/[OAuthProvider](index.md)

# OAuthProvider

[android]\
class [OAuthProvider](index.md)(oAuthHelper: [OAuthHelper](../-o-auth-helper/index.md)) : AuthProvider

## Constructors

| | |
|---|---|
| [OAuthProvider](-o-auth-provider.md) | [android]<br>fun [OAuthProvider](-o-auth-provider.md)(oAuthHelper: [OAuthHelper](../-o-auth-helper/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getCredentialHeaders](get-credential-headers.md) | [android]<br>open suspend override fun [getCredentialHeaders](get-credential-headers.md)(): Map&lt;String, String&gt; |
| [isAccountAuthorized](is-account-authorized.md) | [android]<br>open override fun [isAccountAuthorized](is-account-authorized.md)(): Boolean |
| [withValidAuthToken](with-valid-auth-token.md) | [android]<br>open override fun [withValidAuthToken](with-valid-auth-token.md)(block: (String) -&gt; Unit) |
