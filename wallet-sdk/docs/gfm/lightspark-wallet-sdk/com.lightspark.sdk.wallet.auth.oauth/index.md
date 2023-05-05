//[lightspark-wallet-sdk](../../index.md)/[com.lightspark.sdk.wallet.auth.oauth](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [DataStoreOAuthStateStorage](-data-store-o-auth-state-storage/index.md) | [android]<br>class [DataStoreOAuthStateStorage](-data-store-o-auth-state-storage/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [OAuthStateStorage](-o-auth-state-storage/index.md) |
| [OAuthHelper](-o-auth-helper/index.md) | [android]<br>class [OAuthHelper](-o-auth-helper/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), OAuthStateStorage: [OAuthStateStorage](-o-auth-state-storage/index.md) = SharedPrefsOAuthStateStorage(context), serverEnvironment: ServerEnvironment = ServerEnvironment.DEV) |
| [OAuthProvider](-o-auth-provider/index.md) | [android]<br>class [OAuthProvider](-o-auth-provider/index.md)(oAuthHelper: [OAuthHelper](-o-auth-helper/index.md)) : AuthProvider |
| [OAuthStateStorage](-o-auth-state-storage/index.md) | [android]<br>interface [OAuthStateStorage](-o-auth-state-storage/index.md) |
| [SharedPrefsOAuthStateStorage](-shared-prefs-o-auth-state-storage/index.md) | [android]<br>class [SharedPrefsOAuthStateStorage](-shared-prefs-o-auth-state-storage/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [OAuthStateStorage](-o-auth-state-storage/index.md) |
