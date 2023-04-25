//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[AccountToNodesConnection](index.md)/[AccountToNodesConnection](-account-to-nodes-connection.md)

# AccountToNodesConnection

[common]\
fun [AccountToNodesConnection](-account-to-nodes-connection.md)(pageInfo: [PageInfo](../-page-info/index.md), count: Int, entities: List&lt;[LightsparkNode](../-lightspark-node/index.md)&gt;, purpose: [LightsparkNodePurpose](../-lightspark-node-purpose/index.md)? = null)

#### Parameters

common

| | |
|---|---|
| pageInfo | An object that holds pagination information about the objects in this connection. |
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The nodes for the current page of this connection. |
| purpose | The main purpose for the selected set of nodes. It is automatically determined from the nodes that are selected in this connection and is used for optimization purposes, as well as to determine the variation of the UI that should be presented to the user. |
