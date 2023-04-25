//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.auth](../index.md)/[OAuthHelper](index.md)

# OAuthHelper

[android]\
class [OAuthHelper](index.md)(context: Context, authStateStorage: [AuthStateStorage](../-auth-state-storage/index.md) = SharedPrefsAuthStateStorage(context), serverEnvironment: ServerEnvironment = ServerEnvironment.DEV)

## Constructors

| | |
|---|---|
| [OAuthHelper](-o-auth-helper.md) | [android]<br>fun [OAuthHelper](-o-auth-helper.md)(context: Context, authStateStorage: [AuthStateStorage](../-auth-state-storage/index.md) = SharedPrefsAuthStateStorage(context), serverEnvironment: ServerEnvironment = ServerEnvironment.DEV) |

## Functions

| Name | Summary |
|---|---|
| [endSession](end-session.md) | [android]<br>fun [endSession](end-session.md)(completedIntent: PendingIntent, redirectUri: String? = null, canceledIntent: PendingIntent? = null) |
| [fetchAndPersistRefreshToken](fetch-and-persist-refresh-token.md) | [android]<br>fun [fetchAndPersistRefreshToken](fetch-and-persist-refresh-token.md)(clientSecret: String, callback: (String?, Exception?) -&gt; Unit) |
| [getFreshAuthToken](get-fresh-auth-token.md) | [android]<br>suspend fun [getFreshAuthToken](get-fresh-auth-token.md)(): String |
| [handleAuthResponse](handle-auth-response.md) | [android]<br>fun [handleAuthResponse](handle-auth-response.md)(intent: Intent) |
| [handleAuthResponseAndRequestToken](handle-auth-response-and-request-token.md) | [android]<br>fun [handleAuthResponseAndRequestToken](handle-auth-response-and-request-token.md)(response: Intent, clientSecret: String, callback: (String?, Exception?) -&gt; Unit) |
| [isAuthorized](is-authorized.md) | [android]<br>fun [isAuthorized](is-authorized.md)(): Boolean |
| [launchAuthFlow](launch-auth-flow.md) | [android]<br>fun [launchAuthFlow](launch-auth-flow.md)(clientId: String, redirectUri: String, completedIntent: PendingIntent, canceledIntent: PendingIntent? = null) |
| [parseEndSessionResponse](parse-end-session-response.md) | [android]<br>fun [parseEndSessionResponse](parse-end-session-response.md)(intent: Intent): String |
| [setServerEnvironment](set-server-environment.md) | [android]<br>fun [setServerEnvironment](set-server-environment.md)(environment: ServerEnvironment): Boolean |
| [withAuthToken](with-auth-token.md) | [android]<br>fun [withAuthToken](with-auth-token.md)(block: (String, String) -&gt; Unit) |
