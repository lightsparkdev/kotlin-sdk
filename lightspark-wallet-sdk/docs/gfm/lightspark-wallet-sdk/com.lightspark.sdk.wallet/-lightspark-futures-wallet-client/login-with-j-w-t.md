//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkFuturesWalletClient](index.md)/[loginWithJWT](login-with-j-w-t.md)

# loginWithJWT

[commonJvmAndroid]\
fun [loginWithJWT](login-with-j-w-t.md)(accountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), jwt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;LoginWithJWTOutput&gt;

Login using the Custom JWT authentication scheme described in our documentation.

Note: When using this method, you are responsible for refreshing the JWT token before or when it expires. If the token expires, the client will throw a LightsparkAuthenticationException on the next API call which requires valid authentication. Then you'll need to call this method again to get a new token.

#### Return

The output of the login operation, including the access token, expiration time, and wallet info.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| accountId | The account ID to login with. This is specific to your company's account. |
| jwt | The JWT to use for authentication of this user. |

#### Throws

| | |
|---|---|
| LightsparkException | if the login fails. |
