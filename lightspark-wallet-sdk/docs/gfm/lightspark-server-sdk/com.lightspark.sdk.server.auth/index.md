//[lightspark-server-sdk](../../index.md)/[com.lightspark.sdk.server.auth](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AccountApiTokenAuthProvider](-account-api-token-auth-provider/index.md) | [common]<br>class [AccountApiTokenAuthProvider](-account-api-token-auth-provider/index.md)(tokenId: String, tokenSecret: String) : AuthProvider |
| [AuthStateStorage](-auth-state-storage/index.md) | [android]<br>interface [AuthStateStorage](-auth-state-storage/index.md) |
| [DataStoreAuthStateStorage](-data-store-auth-state-storage/index.md) | [android]<br>class [DataStoreAuthStateStorage](-data-store-auth-state-storage/index.md)(context: Context) : [AuthStateStorage](-auth-state-storage/index.md) |
| [OAuthHelper](-o-auth-helper/index.md) | [android]<br>class [OAuthHelper](-o-auth-helper/index.md)(context: Context, authStateStorage: [AuthStateStorage](-auth-state-storage/index.md) = SharedPrefsAuthStateStorage(context), serverEnvironment: ServerEnvironment = ServerEnvironment.DEV) |
| [OAuthProvider](-o-auth-provider/index.md) | [android]<br>class [OAuthProvider](-o-auth-provider/index.md)(oAuthHelper: [OAuthHelper](-o-auth-helper/index.md)) : AuthProvider |
| [SharedPrefsAuthStateStorage](-shared-prefs-auth-state-storage/index.md) | [android]<br>class [SharedPrefsAuthStateStorage](-shared-prefs-auth-state-storage/index.md)(context: Context) : [AuthStateStorage](-auth-state-storage/index.md) |
