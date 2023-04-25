## Lightspark Kotlin SDK 🤖

This is a Kotlin (multiplatform) SDK for the Lightspark API. It is currently in development and not
ready for production use. It also contains a sample Android app which uses the API to display basic
node, channel, and transaction information. In the future, it will also be used to create a
full-featured Android wallet.

### Usage

> NOTE: The SDK is currently not published to any package repositories, but will be soon... Once it
> is, we'll add instructions for including the dependency in your project.

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

To initialize a client, you'll need to use the 

```kotlin

// Initialize the client with account token info:
val lightsparkClient = LightsparkClient.Builder().apply {
    tokenId = "your-token-id"
    token = "your-secret-token"
}.build()

// An example API call fetching the dashboard info for the active account:
val dashboard = lightsparkClient.getDashboard()
```

### Building and running the sample app

You can build the SDK and sample app using Gradle or Android Studio. The easiest option is to open
this root directory as a project in Android studio and run the `androiddemo` app configuration on an
Android device or emulator.

### Updating the graphql schema

For now, you can update the graphql schema by running the `shared:updateGraphQLSchema` gradle task.
This will download the latest schema directly from the webdev git repo and replace the existing
schema.graphql file in the shared module. In the future, we should consider moving the schema to an
artifact registry where it can be versioned and tracked correctly.
