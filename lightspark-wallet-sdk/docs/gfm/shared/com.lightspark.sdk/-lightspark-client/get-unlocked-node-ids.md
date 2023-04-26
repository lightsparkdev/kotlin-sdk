//[shared](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkClient](index.md)/[getUnlockedNodeIds](get-unlocked-node-ids.md)

# getUnlockedNodeIds

[common]\
fun [getUnlockedNodeIds](get-unlocked-node-ids.md)(): Flow&lt;[Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;

#### Return

A Flow that emits the set of node IDs that have been unlocked via the [recoverNodeSigningKey](recover-node-signing-key.md) function.