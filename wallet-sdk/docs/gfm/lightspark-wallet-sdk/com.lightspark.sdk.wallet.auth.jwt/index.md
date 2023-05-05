//[lightspark-wallet-sdk](../../index.md)/[com.lightspark.sdk.wallet.auth.jwt](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [CustomJwtAuthProvider](-custom-jwt-auth-provider/index.md) | [common]<br>class [CustomJwtAuthProvider](-custom-jwt-auth-provider/index.md)(tokenStorage: [JwtStorage](-jwt-storage/index.md)) : AuthProvider<br>A custom AuthProvider that uses a JWT token to authenticate requests. |
| [DataStoreJwtStorage](-data-store-jwt-storage/index.md) | [android]<br>class [DataStoreJwtStorage](-data-store-jwt-storage/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : JwtStorage |
| [InMemoryJwtStorage](-in-memory-jwt-storage/index.md) | [common]<br>class [InMemoryJwtStorage](-in-memory-jwt-storage/index.md) : [JwtStorage](-jwt-storage/index.md) |
| [JwtStorage](-jwt-storage/index.md) | [common]<br>interface [JwtStorage](-jwt-storage/index.md) |
| [JwtTokenInfo](-jwt-token-info/index.md) | [common]<br>@Serializable<br>data class [JwtTokenInfo](-jwt-token-info/index.md)(val accessToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val validUntil: Instant) |
| [SharedPrefsJwtStorage](-shared-prefs-jwt-storage/index.md) | [android]<br>class [SharedPrefsJwtStorage](-shared-prefs-jwt-storage/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : JwtStorage |
