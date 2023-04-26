## Lightspark Kotlin SDK 🤖

This is a Kotlin (multiplatform) Wallet SDK for the Lightspark API. It can be used from an Android
environment to integrate with a Lightspark Lightning wallet.

### Usage

> NOTE: The SDK is currently not published to any package repositories, but will be soon... Once it
> is, we'll add instructions for including the dependency in your project.

To use the SDK, you'll need to create a client instance. The SDK offers several client
implementations which are suitable for different environments and needs:

- `LightsparkCoroutinesWalletClient` - A client which uses the `kotlinx.coroutines` library to make
  asynchronous API calls. This is the recommended client for Android and other environments using
  Kotlin.
- `LightsparkSyncWalletClient` - A fully synchronous client implementation which can be used to make
  blocking calls in an environment where you want more control over your concurrency model.
- `LightsparkFuturesWalletClient` - A client which returns `java.util.concurrent.CompletableFuture`
  results for asynchronous API calls. This is the recommended client for Java environments using JDK
  8+.

To initialize a client, you'll need to use the `ClientConfig` object. 

```kotlin
// Initialize a new client:
val lightsparkClient = LightsparkCoroutinesWalletClient(ClientConfig())

// Log in using a custom JWT login:
val loginResults = lightsparkClient.loginWithJWT("your-account-id", "your-jwt-token")
Log.d("Lightspark", "Current wallet info: ${loginResults.wallet}")

// An example API call fetching the dashboard info for the active wallet:
val dashboard = lightsparkClient.getWalletDashboard()
```

or in Java using the synchronous client:

```java
// Initialize the client with account token info:
LightsparkSyncClient lightsparkClient = new LightsparkSyncClient(
    new ClientConfig()
        .setAuthProvider(new AccountApiTokenAuthProvider("your-token-id","your-secret-token"))
);

// Log in using a custom JWT login:
LoginWithJWTOutput loginResults = lightsparkClient.loginWithJWT("your-account-id", "your-jwt-token");
Log.d("Lightspark", "Current wallet info: " + loginResults.wallet.toString());

// An example API call fetching the dashboard info for the active wallet:
WalletDashboard dashboard = lightsparkClient.getWalletDashboard();
```

or in Java using the Futures client:
```java
LightsparkFuturesClient lightsparkClient = new LightsparkFuturesClient(
    new ClientConfig()
        .setAuthProvider(new AccountApiTokenAuthProvider("your-token-id","your-secret-token"))
);

// Log in using a custom JWT login:
LoginWithJWTOutput loginResults = lightsparkClient.loginWithJWT("your-account-id", "your-jwt-token");
Log.d("Lightspark", "Current wallet info: " + loginResults.wallet.toString());

// An example API call fetching the dashboard info for the active wallet:
WalletDashboard dashboard = lightsparkClient.getWalletDashboard().get(5, TimeUnit.SECONDS);
```

### Building and running the sample app

You can build the SDK and sample app using Gradle or Android Studio. The easiest option is to open
this root directory as a project in Android studio and run the `androiddemo` app configuration on an
Android device or emulator.
