//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkCoroutinesClient](index.md)/[createNodeWalletAddress](create-node-wallet-address.md)

# createNodeWalletAddress

[common]\
suspend fun [createNodeWalletAddress](create-node-wallet-address.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Creates an L1 Bitcoin wallet address for a given node which can be used to deposit or withdraw funds.

#### Return

The newly created L1 wallet address.

#### Parameters

common

| | |
|---|---|
| nodeId | The ID of the node to create the wallet address for. |