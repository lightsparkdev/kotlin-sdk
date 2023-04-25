//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[ApiToken](index.md)

# ApiToken

[common]\
@Serializable

data class [ApiToken](index.md)(val id: String, val createdAt: Instant, val updatedAt: Instant, val clientId: String, val name: String, val permissions: List&lt;[Permission](../-permission/index.md)&gt;) : [Entity](../-entity/index.md)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| clientId | An opaque identifier that should be used as a client_id (or username) in the HTTP Basic Authentication scheme when issuing requests against the Lightspark API. |
| name | An arbitrary name chosen by the creator of the token to help identify the token in the list of tokens that have been created for the account. |
| permissions | A list of permissions granted to the token. |

## Constructors

| | |
|---|---|
| [ApiToken](-api-token.md) | [common]<br>fun [ApiToken](-api-token.md)(id: String, createdAt: Instant, updatedAt: Instant, clientId: String, name: String, permissions: List&lt;[Permission](../-permission/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [clientId](client-id.md) | [common]<br>val [clientId](client-id.md): String |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [id](id.md) | [common]<br>open override val [id](id.md): String |
| [name](name.md) | [common]<br>val [name](name.md): String |
| [permissions](permissions.md) | [common]<br>val [permissions](permissions.md): List&lt;[Permission](../-permission/index.md)&gt; |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
