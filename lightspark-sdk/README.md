# Module lightspark-sdk

## Lightspark Kotlin SDK 🤖

![Maven Central](https://img.shields.io/maven-central/v/com.lightspark/lightspark-sdk)

This is a Kotlin (multiplatform) SDK which is a simple wrapper around the Lightspark GraphQL server
API. It's used to manage accounts, nodes, wallets, etc. It can authenticate using an Account API
token retrieved from your Lightspark account dashboard or via the oauth module.

[API Reference](https://app.lightspark.com/docs/reference/kotlin/lightspark-sdk/index.html)

### Getting Started

Start by installing the SDK from maven:

**build.gradle:**
```groovy
  dependencies {
    implementation "com.lightspark:lightspark-sdk:0.19.0"
  }
```

or with **build.gradle.kts:**
```kotlin
  dependencies {
    implementation("com.lightspark:lightspark-sdk:0.19.0")
  }
```

To use the SDK, you'll need to create a client instance. The SDK offers several client
implementations which are suitable for different environments and needs:

- `LightsparkCoroutinesClient` - A client which uses the `kotlinx.coroutines` library to make
  asynchronous API calls. This is the recommended client for Android and other environments using
  Kotlin.
- `LightsparkSyncClient` - A fully synchronous client implementation which can be used to make
  blocking calls in an environment where you want more control over your concurrency model.
- `LightsparkFuturesClient` - A client which returns `java.util.concurrent.CompletableFuture`
  results for asynchronous API calls. This is the recommended client for Java environments using JDK
  8+.

To initialize a client, you'll need to use the `ClientConfig` object.

```kotlin
// Initialize the client with account token info:
val lightsparkClient = LightsparkCoroutinesClient(
    ClientConfig(
        authProvider = AccountApiTokenAuthProvider(
            tokenId = "your-token-id",
            token = "your-secret-token"
        )
    )
)

// An example API call fetching the dashboard info for the active account:
val dashboard = lightsparkClient.getFullAccountDashboard()
```

or in Java using the synchronous client:

```java
// Initialize the client with account token info:
LightsparkSyncClient lightsparkClient = new LightsparkSyncClient(
    new ClientConfig()
        .setAuthProvider(new AccountApiTokenAuthProvider("your-token-id","your-secret-token"))
);

// An example API call fetching the dashboard info for the active account:
MultiNodeDashboard dashboard = lightsparkClient.getFullAccountDashboard();
```

or in Java using the Futures client:
```java
LightsparkFuturesClient lightsparkClient = new LightsparkFuturesClient(
    new ClientConfig()
        .setAuthProvider(new AccountApiTokenAuthProvider("your-token-id","your-secret-token"))
);

// An example API call fetching the dashboard info for the active account:
MultiNodeDashboard dashboard = lightsparkClient.getFullAccountDashboard().get(5, TimeUnit.SECONDS);
```

### Running E2E tests

You can see a set of API calls in ClientIntegrationTests.kt and can run them yourself after
configuring the following environment variables:

- `LIGHTSPARK_API_TOKEN_CLIENT_ID` - The client ID of an API token generated from your Lightspark
dashboard from the [API Config Page](https://app.lightspark.com/api-config).
- `LIGHTSPARK_API_TOKEN_CLIENT_SECRET` - The client secret of that same API token.

The test cases cover things like creating and paying invoices, getting node and channel info, etc.

For examples of some API calls in Java, see the `kotlin-sdk/javatest` directory, which uses the SDK
to make several test calls using both Java-friendly client implementations in
`LightsparkFuturesClientTest.kt` and `LightsparkSyncClientTest.kt`.
