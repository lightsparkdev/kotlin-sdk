//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[ApiToken](index.md)/[ApiToken](-api-token.md)

# ApiToken

[common]\
fun [ApiToken](-api-token.md)(id: String, createdAt: Instant, updatedAt: Instant, clientId: String, name: String, permissions: List&lt;[Permission](../-permission/index.md)&gt;)

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
