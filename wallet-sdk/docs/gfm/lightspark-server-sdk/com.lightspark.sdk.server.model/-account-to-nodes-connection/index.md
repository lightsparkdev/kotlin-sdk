//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[AccountToNodesConnection](index.md)

# AccountToNodesConnection

[common]\
@Serializable

data class [AccountToNodesConnection](index.md)(val pageInfo: [PageInfo](../-page-info/index.md), val count: Int, val entities: List&lt;[LightsparkNode](../-lightspark-node/index.md)&gt;, val purpose: [LightsparkNodePurpose](../-lightspark-node-purpose/index.md)? = null)

A connection between an account and the nodes it manages.

#### Parameters

common

| | |
|---|---|
| pageInfo | An object that holds pagination information about the objects in this connection. |
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The nodes for the current page of this connection. |
| purpose | The main purpose for the selected set of nodes. It is automatically determined from the nodes that are selected in this connection and is used for optimization purposes, as well as to determine the variation of the UI that should be presented to the user. |

## Constructors

| | |
|---|---|
| [AccountToNodesConnection](-account-to-nodes-connection.md) | [common]<br>fun [AccountToNodesConnection](-account-to-nodes-connection.md)(pageInfo: [PageInfo](../-page-info/index.md), count: Int, entities: List&lt;[LightsparkNode](../-lightspark-node/index.md)&gt;, purpose: [LightsparkNodePurpose](../-lightspark-node-purpose/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [count](count.md) | [common]<br>val [count](count.md): Int |
| [entities](entities.md) | [common]<br>val [entities](entities.md): List&lt;[LightsparkNode](../-lightspark-node/index.md)&gt; |
| [pageInfo](page-info.md) | [common]<br>val [pageInfo](page-info.md): [PageInfo](../-page-info/index.md) |
| [purpose](purpose.md) | [common]<br>val [purpose](purpose.md): [LightsparkNodePurpose](../-lightspark-node-purpose/index.md)? = null |
