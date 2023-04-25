//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkFuturesClient](index.md)/[recoverNodeSigningKey](recover-node-signing-key.md)

# recoverNodeSigningKey

[commonJvmAndroid]\
suspend fun [recoverNodeSigningKey](recover-node-signing-key.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), nodePassword: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;

Unlocks a node for use with the SDK for the current application session. This function must be called before any other functions that require node signing keys, including [payInvoice](pay-invoice.md).

#### Return

True if the node was successfully unlocked, false otherwise.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| nodeId | The ID of the node to unlock. |
| nodePassword | The password for the node. |
