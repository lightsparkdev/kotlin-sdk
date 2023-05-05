//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[NodeToAddressesConnection](index.md)/[NodeToAddressesConnection](-node-to-addresses-connection.md)

# NodeToAddressesConnection

[common]\
fun [NodeToAddressesConnection](-node-to-addresses-connection.md)(count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[NodeAddress](../-node-address/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The addresses for the current page of this connection. |
