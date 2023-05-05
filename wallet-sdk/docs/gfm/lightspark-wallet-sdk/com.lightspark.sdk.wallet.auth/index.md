//[lightspark-wallet-sdk](../../index.md)/[com.lightspark.sdk.wallet.auth](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AuthStateStorage](-auth-state-storage/index.md) | [android]<br>interface [AuthStateStorage](-auth-state-storage/index.md) |
| [CustomJwtAuthProvider](-custom-jwt-auth-provider/index.md) | [common]<br>class [CustomJwtAuthProvider](-custom-jwt-auth-provider/index.md)(accessToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), validUntil: Instant) : AuthProvider<br>A custom AuthProvider that uses a JWT token to authenticate requests. |
| [DataStoreAuthStateStorage](-data-store-auth-state-storage/index.md) | [android]<br>class [DataStoreAuthStateStorage](-data-store-auth-state-storage/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [AuthStateStorage](-auth-state-storage/index.md) |
| [OAuthHelper](-o-auth-helper/index.md) | [android]<br>class [OAuthHelper](-o-auth-helper/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), authStateStorage: [AuthStateStorage](-auth-state-storage/index.md) = SharedPrefsAuthStateStorage(context), serverEnvironment: ServerEnvironment = ServerEnvironment.DEV) |
| [OAuthProvider](-o-auth-provider/index.md) | [android]<br>class [OAuthProvider](-o-auth-provider/index.md)(oAuthHelper: [OAuthHelper](-o-auth-helper/index.md)) : AuthProvider |
| [SharedPrefsAuthStateStorage](-shared-prefs-auth-state-storage/index.md) | [android]<br>class [SharedPrefsAuthStateStorage](-shared-prefs-auth-state-storage/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [AuthStateStorage](-auth-state-storage/index.md) |
