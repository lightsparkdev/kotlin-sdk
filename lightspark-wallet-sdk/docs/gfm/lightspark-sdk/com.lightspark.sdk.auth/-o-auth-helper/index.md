//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.auth](../index.md)/[OAuthHelper](index.md)

# OAuthHelper

[android]\
class [OAuthHelper](index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), authStateStorage: [AuthStateStorage](../-auth-state-storage/index.md) = SharedPrefsAuthStateStorage(context), serverEnvironment: ServerEnvironment = ServerEnvironment.DEV)

## Constructors

| | |
|---|---|
| [OAuthHelper](-o-auth-helper.md) | [android]<br>fun [OAuthHelper](-o-auth-helper.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), authStateStorage: [AuthStateStorage](../-auth-state-storage/index.md) = SharedPrefsAuthStateStorage(context), serverEnvironment: ServerEnvironment = ServerEnvironment.DEV) |

## Functions

| Name | Summary |
|---|---|
| [endSession](end-session.md) | [android]<br>fun [endSession](end-session.md)(completedIntent: [PendingIntent](https://developer.android.com/reference/kotlin/android/app/PendingIntent.html), redirectUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, canceledIntent: [PendingIntent](https://developer.android.com/reference/kotlin/android/app/PendingIntent.html)? = null) |
| [fetchAndPersistRefreshToken](fetch-and-persist-refresh-token.md) | [android]<br>fun [fetchAndPersistRefreshToken](fetch-and-persist-refresh-token.md)(clientSecret: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), callback: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [getFreshAuthToken](get-fresh-auth-token.md) | [android]<br>suspend fun [getFreshAuthToken](get-fresh-auth-token.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [handleAuthResponse](handle-auth-response.md) | [android]<br>fun [handleAuthResponse](handle-auth-response.md)(intent: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)) |
| [handleAuthResponseAndRequestToken](handle-auth-response-and-request-token.md) | [android]<br>fun [handleAuthResponseAndRequestToken](handle-auth-response-and-request-token.md)(response: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html), clientSecret: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), callback: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [isAuthorized](is-authorized.md) | [android]<br>fun [isAuthorized](is-authorized.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [launchAuthFlow](launch-auth-flow.md) | [android]<br>fun [launchAuthFlow](launch-auth-flow.md)(clientId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), redirectUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), completedIntent: [PendingIntent](https://developer.android.com/reference/kotlin/android/app/PendingIntent.html), canceledIntent: [PendingIntent](https://developer.android.com/reference/kotlin/android/app/PendingIntent.html)? = null) |
| [parseEndSessionResponse](parse-end-session-response.md) | [android]<br>fun [parseEndSessionResponse](parse-end-session-response.md)(intent: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [setServerEnvironment](set-server-environment.md) | [android]<br>fun [setServerEnvironment](set-server-environment.md)(environment: ServerEnvironment): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [withAuthToken](with-auth-token.md) | [android]<br>fun [withAuthToken](with-auth-token.md)(block: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
