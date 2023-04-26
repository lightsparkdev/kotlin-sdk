//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkSyncWalletClient](index.md)/[loginWithJWT](login-with-j-w-t.md)

# loginWithJWT

[common]\
fun [loginWithJWT](login-with-j-w-t.md)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), jwt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), storage: [JwtStorage](../../com.lightspark.sdk.wallet.auth.jwt/-jwt-storage/index.md)): [LoginWithJWTOutput](../../com.lightspark.sdk.wallet.model/-login-with-j-w-t-output/index.md)

Login using the Custom JWT authentication scheme described in our documentation.

Note: When using this method, you are responsible for refreshing the JWT token before or when it expires. If the token expires, the client will throw a LightsparkAuthenticationException on the next API call which requires valid authentication. Then you'll need to call this method again to get a new token.

#### Return

The output of the login operation, including the access token, expiration time, and wallet info.

#### Parameters

common

| | |
|---|---|
| accountId | The account ID to login with. This is specific to your company's account. |
| jwt | The JWT to use for authentication of this user.<br>-     @param storage A [JwtStorage](../../com.lightspark.sdk.wallet.auth.jwt/-jwt-storage/index.md) implementation that will store the new JWT token info. |

#### Throws

| | |
|---|---|
| LightsparkException | if the login fails. |
