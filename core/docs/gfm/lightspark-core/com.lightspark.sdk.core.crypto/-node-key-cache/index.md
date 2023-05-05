//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core.crypto](../index.md)/[NodeKeyCache](index.md)

# NodeKeyCache

[common]\
class [NodeKeyCache](index.md)

## Constructors

| | |
|---|---|
| [NodeKeyCache](-node-key-cache.md) | [common]<br>fun [NodeKeyCache](-node-key-cache.md)() |

## Functions

| Name | Summary |
|---|---|
| [clear](clear.md) | [common]<br>fun [clear](clear.md)() |
| [contains](contains.md) | [common]<br>fun [contains](contains.md)(nodeId: String): Boolean |
| [get](get.md) | [common]<br>operator fun [get](get.md)(nodeId: String): ByteArray |
| [observeCachedNodeIds](observe-cached-node-ids.md) | [common]<br>fun [observeCachedNodeIds](observe-cached-node-ids.md)(): Flow&lt;Set&lt;String&gt;&gt; |
| [remove](remove.md) | [common]<br>fun [remove](remove.md)(nodeId: String) |
| [safeGetKey](safe-get-key.md) | [common]<br>fun [safeGetKey](safe-get-key.md)(nodeId: String): ByteArray? |
| [set](set.md) | [common]<br>operator fun [set](set.md)(nodeId: String, key: ByteArray) |
