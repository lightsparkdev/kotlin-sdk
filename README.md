## Lightspark Kotlin SDK

This is a Kotlin (multiplatform) SDK for the Lightspark API. It is currently in development and not
ready for production use. It also contains a sample Android app which uses the API to display basic
node, channel, and transaction information. In the future, it will also be used to create a
full-featured Android wallet.

### Usage

> NOTE: The SDK is currently not published to any package repositories, but will be soon... Once it is, we'll add instructions for including the dependency in your project.

To use the SDK, you'll need to create a `LightsparkClient` instance. This is done via the `LightsparkClient.Builder` class. The builder requires a `LightsparkConfig` instance, which contains the base URL for the API, and the API key. The API key can be obtained from the [Lightspark Dashboard](https://lightspark.io/dashboard).

```kotlin

### Building and running the sample app

You can build the SDK and sample app using Gradle or Android Studio. The easiest option is to open
this root directory as a project in Android studio and run the `androiddemo` app configuration on an
Android device or emulator.

