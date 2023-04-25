//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkCoroutinesClient](index.md)/[loadNodeSigningKey](load-node-signing-key.md)

# loadNodeSigningKey

[common]\
fun [loadNodeSigningKey](load-node-signing-key.md)(nodeId: String, signingKeyBytesPEM: ByteArray)

Unlocks a node for use with the SDK for the current application session. This function or [recoverNodeSigningKey](recover-node-signing-key.md) must be called before any other functions that require node signing keys, including [payInvoice](pay-invoice.md).

This function is intended for use in cases where the node's private signing key is already saved by the application outside of the SDK. It is the responsibility of the application to ensure that the key is valid and that it is the correct key for the node. Otherwise signed requests will fail.

#### Parameters

common

| | |
|---|---|
| nodeId | The ID of the node to unlock. |
| signingKeyBytesPEM | The PEM encoded bytes of the node's private signing key. |
