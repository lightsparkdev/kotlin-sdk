//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.auth](../index.md)/[AccountApiTokenAuthProvider](index.md)

# AccountApiTokenAuthProvider

[common]\
class [AccountApiTokenAuthProvider](index.md)(tokenId: String, tokenSecret: String) : AuthProvider

## Constructors

| | |
|---|---|
| [AccountApiTokenAuthProvider](-account-api-token-auth-provider.md) | [common]<br>fun [AccountApiTokenAuthProvider](-account-api-token-auth-provider.md)(tokenId: String, tokenSecret: String) |

## Functions

| Name | Summary |
|---|---|
| [getCredentialHeaders](get-credential-headers.md) | [common]<br>open suspend override fun [getCredentialHeaders](get-credential-headers.md)(): Map&lt;String, String&gt; |
| [isAccountAuthorized](is-account-authorized.md) | [common]<br>open override fun [isAccountAuthorized](is-account-authorized.md)(): Boolean |
| [withValidAuthToken](with-valid-auth-token.md) | [common]<br>open override fun [withValidAuthToken](with-valid-auth-token.md)(block: (String) -&gt; Unit) |
