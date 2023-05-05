//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.auth.jwt](../index.md)/[JwtStorage](index.md)

# JwtStorage

[common]\
interface [JwtStorage](index.md)

## Functions

| Name | Summary |
|---|---|
| [getCurrent](get-current.md) | [common]<br>abstract fun [getCurrent](get-current.md)(): [JwtTokenInfo](../-jwt-token-info/index.md)? |
| [replace](replace.md) | [common]<br>abstract fun [replace](replace.md)(state: [JwtTokenInfo](../-jwt-token-info/index.md)?) |

## Inheritors

| Name |
|---|
| [DataStoreJwtStorage](../-data-store-jwt-storage/index.md) |
| [SharedPrefsJwtStorage](../-shared-prefs-jwt-storage/index.md) |
| [InMemoryJwtStorage](../-in-memory-jwt-storage/index.md) |
