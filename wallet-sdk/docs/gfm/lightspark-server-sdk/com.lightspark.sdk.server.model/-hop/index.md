//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Hop](index.md)

# Hop

[common]\
@Serializable

data class [Hop](index.md)(val id: String, val createdAt: Instant, val updatedAt: Instant, val index: Int, val destinationId: [EntityId](../-entity-id/index.md)? = null, val publicKey: String? = null, val amountToForward: [CurrencyAmount](../-currency-amount/index.md)? = null, val fee: [CurrencyAmount](../-currency-amount/index.md)? = null, val expiryBlockHeight: Int? = null) : [Entity](../-entity/index.md)

One hop signifies a payment moving one node ahead on a payment route; a list of sequential hops defines the path from sender node to recipient node for a payment attempt.

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

## Constructors

| | |
|---|---|
| [Hop](-hop.md) | [common]<br>fun [Hop](-hop.md)(id: String, createdAt: Instant, updatedAt: Instant, index: Int, destinationId: [EntityId](../-entity-id/index.md)? = null, publicKey: String? = null, amountToForward: [CurrencyAmount](../-currency-amount/index.md)? = null, fee: [CurrencyAmount](../-currency-amount/index.md)? = null, expiryBlockHeight: Int? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountToForward](amount-to-forward.md) | [common]<br>val [amountToForward](amount-to-forward.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [destinationId](destination-id.md) | [common]<br>val [destinationId](destination-id.md): [EntityId](../-entity-id/index.md)? = null |
| [expiryBlockHeight](expiry-block-height.md) | [common]<br>val [expiryBlockHeight](expiry-block-height.md): Int? = null |
| [fee](fee.md) | [common]<br>val [fee](fee.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [id](id.md) | [common]<br>open override val [id](id.md): String |
| [index](--index--.md) | [common]<br>val [index](--index--.md): Int |
| [publicKey](public-key.md) | [common]<br>val [publicKey](public-key.md): String? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
