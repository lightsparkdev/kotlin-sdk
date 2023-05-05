//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkFuturesClient](index.md)/[fundNode](fund-node.md)

# fundNode

[commonJvmAndroid]\
fun [fundNode](fund-node.md)(nodeId: String, amountSats: Long?): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;

Adds funds to a Lightspark node on the REGTEST network. If the amount is not specified, 10,000,000 SATOSHI will be added. This API only functions for nodes created on the REGTEST network and will return an error when called for any non-REGTEST node.

#### Return

The amount of funds added to the node.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| nodeId | The ID of the node to fund. Must be a REGTEST node. |
| amountSats | The amount of funds to add to the node. Defaults to 10,000,000 SATOSHI. |
