# Lightspark Kotlin SDKs 🤖

This repository contains the Lightspark Kotlin (multiplatform) SDKs. It also contains a sample
Android app which uses the API to display basic node, channel, and transaction information.

## SDKs

### lightspark-sdk

![Maven Central](https://img.shields.io/maven-central/v/com.lightspark/lightspark-sdk)

This is a Kotlin (multiplatform) SDK which is a simple wrapper around the Lightspark GraphQL server
API. It's used to manage accounts, nodes, wallets, etc. It can authenticate using an Account API
token retrieved from your Lightspark account dashboard or via the oauth module.
See `lightspark-sdk/README.md` for more info.

### wallet-sdk

![Maven Central](https://img.shields.io/maven-central/v/com.lightspark/wallet-sdk)

An SDK for interacting with the Lightspark API for a single wallet user. It can be used from an
Android environment and is used by the sample app in this repository. See its README for more info.

### Building and running the sample app

You can build the SDK and sample app using Gradle or Android Studio. The easiest option is to open
this root directory as a project in Android studio and run the `androidwalletdemo` app configuration
on an
Android device or emulator.
