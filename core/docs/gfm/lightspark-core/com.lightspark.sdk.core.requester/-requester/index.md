//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core.requester](../index.md)/[Requester](index.md)

# Requester

[common]\
class [Requester](index.md)(nodeKeyCache: [NodeKeyCache](../../com.lightspark.sdk.core.crypto/-node-key-cache/index.md), authProvider: [AuthProvider](../../com.lightspark.sdk.core.auth/-auth-provider/index.md), jsonSerialFormat: Json, baseUrl: String = DEFAULT_BASE_URL)

## Constructors

| | |
|---|---|
| [Requester](-requester.md) | [common]<br>fun [Requester](-requester.md)(nodeKeyCache: [NodeKeyCache](../../com.lightspark.sdk.core.crypto/-node-key-cache/index.md), authProvider: [AuthProvider](../../com.lightspark.sdk.core.auth/-auth-provider/index.md), jsonSerialFormat: Json, baseUrl: String = DEFAULT_BASE_URL) |

## Functions

| Name | Summary |
|---|---|
| [executeQuery](execute-query.md) | [common]<br>suspend fun &lt;[T](execute-query.md)&gt; [executeQuery](execute-query.md)(query: [Query](../-query/index.md)&lt;[T](execute-query.md)&gt;): [T](execute-query.md)? |
| [makeRawRequest](make-raw-request.md) | [common]<br>suspend fun [makeRawRequest](make-raw-request.md)(queryPayload: String, variables: JsonObject? = null, signingNodeId: String? = null): JsonObject? |
