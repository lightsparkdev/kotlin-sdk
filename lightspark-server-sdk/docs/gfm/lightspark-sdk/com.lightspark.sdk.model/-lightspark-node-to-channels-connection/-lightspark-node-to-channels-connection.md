//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[LightsparkNodeToChannelsConnection](index.md)/[LightsparkNodeToChannelsConnection](-lightspark-node-to-channels-connection.md)

# LightsparkNodeToChannelsConnection

[common]\
fun [LightsparkNodeToChannelsConnection](-lightspark-node-to-channels-connection.md)(pageInfo: [PageInfo](../-page-info/index.md), count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Channel](../-channel/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| pageInfo | An object that holds pagination information about the objects in this connection. |
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The channels for the current page of this connection. |
