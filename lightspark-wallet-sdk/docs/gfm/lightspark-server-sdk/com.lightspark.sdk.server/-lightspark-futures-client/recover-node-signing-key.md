//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkFuturesClient](index.md)/[recoverNodeSigningKey](recover-node-signing-key.md)

# recoverNodeSigningKey

[commonJvmAndroid]\
suspend fun [recoverNodeSigningKey](recover-node-signing-key.md)(nodeId: String, nodePassword: String): &lt;Error class: unknown class&gt;&lt;Boolean&gt;

Unlocks a node for use with the SDK for the current application session. This function must be called before any other functions that require node signing keys, including [payInvoice](pay-invoice.md).

#### Return

True if the node was successfully unlocked, false otherwise.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| nodeId | The ID of the node to unlock. |
| nodePassword | The password for the node. |
