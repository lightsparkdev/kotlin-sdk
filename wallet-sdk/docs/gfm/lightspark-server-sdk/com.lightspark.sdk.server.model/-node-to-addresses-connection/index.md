//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[NodeToAddressesConnection](index.md)

# NodeToAddressesConnection

[common]\
@Serializable

data class [NodeToAddressesConnection](index.md)(val count: Int, val entities: List&lt;[NodeAddress](../-node-address/index.md)&gt;)

A connection between a node and the addresses it has announced for itself on Lightning Network.

#### Parameters

common

| | |
|---|---|
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The addresses for the current page of this connection. |

## Constructors

| | |
|---|---|
| [NodeToAddressesConnection](-node-to-addresses-connection.md) | [common]<br>fun [NodeToAddressesConnection](-node-to-addresses-connection.md)(count: Int, entities: List&lt;[NodeAddress](../-node-address/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [count](count.md) | [common]<br>val [count](count.md): Int |
| [entities](entities.md) | [common]<br>val [entities](entities.md): List&lt;[NodeAddress](../-node-address/index.md)&gt; |
