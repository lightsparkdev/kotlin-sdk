//[lightspark-sdk](../../index.md)/[com.lightspark.sdk.auth](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AccountApiTokenAuthProvider](-account-api-token-auth-provider/index.md) | [common]<br>class [AccountApiTokenAuthProvider](-account-api-token-auth-provider/index.md)(tokenId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tokenSecret: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [AuthProvider](-auth-provider/index.md) |
| [AuthProvider](-auth-provider/index.md) | [common]<br>interface [AuthProvider](-auth-provider/index.md) |
| [AuthStateStorage](-auth-state-storage/index.md) | [android]<br>interface [AuthStateStorage](-auth-state-storage/index.md) |
| [DataStoreAuthStateStorage](-data-store-auth-state-storage/index.md) | [android]<br>class [DataStoreAuthStateStorage](-data-store-auth-state-storage/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [AuthStateStorage](-auth-state-storage/index.md) |
| [LightsparkAuthenticationException](-lightspark-authentication-exception/index.md) | [common]<br>class [LightsparkAuthenticationException](-lightspark-authentication-exception/index.md) : [LightsparkException](../com.lightspark.sdk/-lightspark-exception/index.md) |
| [OAuthHelper](-o-auth-helper/index.md) | [android]<br>class [OAuthHelper](-o-auth-helper/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), authStateStorage: [AuthStateStorage](-auth-state-storage/index.md) = SharedPrefsAuthStateStorage(context), serverEnvironment: ServerEnvironment = ServerEnvironment.DEV) |
| [OAuthProvider](-o-auth-provider/index.md) | [android]<br>class [OAuthProvider](-o-auth-provider/index.md)(oAuthHelper: [OAuthHelper](-o-auth-helper/index.md)) : AuthProvider |
| [SharedPrefsAuthStateStorage](-shared-prefs-auth-state-storage/index.md) | [android]<br>class [SharedPrefsAuthStateStorage](-shared-prefs-auth-state-storage/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [AuthStateStorage](-auth-state-storage/index.md) |
| [StubAuthProvider](-stub-auth-provider/index.md) | [common]<br>class [StubAuthProvider](-stub-auth-provider/index.md) : [AuthProvider](-auth-provider/index.md)<br>A stub implementation of [AuthProvider](-auth-provider/index.md) that does not provide any authentication. It always stays unauthorized. |
