//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Hop](index.md)/[Hop](-hop.md)

# Hop

[common]\
fun [Hop](-hop.md)(id: String, createdAt: Instant, updatedAt: Instant, index: Int, destinationId: [EntityId](../-entity-id/index.md)? = null, publicKey: String? = null, amountToForward: [CurrencyAmount](../-currency-amount/index.md)? = null, fee: [CurrencyAmount](../-currency-amount/index.md)? = null, expiryBlockHeight: Int? = null)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| index | The zero-based index position of this hop in the path |
| destinationId | The destination node of the hop. |
| publicKey | The public key of the node to which the hop is bound. |
| amountToForward | The amount that is to be forwarded to the destination node. |
| fee | The fees to be collected by the source node for forwarding the payment over the hop. |
| expiryBlockHeight | The block height at which an unsettled HTLC is considered expired. |