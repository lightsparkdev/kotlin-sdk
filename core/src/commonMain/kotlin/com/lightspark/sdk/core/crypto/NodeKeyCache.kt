package com.lightspark.sdk.core.crypto

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NodeKeyCache {
    private val keyCache = mutableMapOf<String, SigningKey>()
    private val unlockedNodesFlow = MutableStateFlow<Set<String>>(emptySet())

    fun contains(nodeId: String): Boolean {
        return keyCache.containsKey(nodeId)
    }

    operator fun get(nodeId: String): SigningKey {
        return keyCache[nodeId] ?: throw MissingKeyException(nodeId)
    }

    operator fun set(nodeId: String, key: SigningKey) {
        keyCache[nodeId] = key
        onCachedKeysChanged()
    }

    private fun onCachedKeysChanged() {
        val didWork = unlockedNodesFlow.tryEmit(keyCache.keys)
        if (!didWork) {
            unlockedNodesFlow.value = keyCache.keys
        }
    }

    fun remove(nodeId: String) {
        keyCache.remove(nodeId)
        onCachedKeysChanged()
    }

    fun clear() {
        keyCache.clear()
        onCachedKeysChanged()
    }

    fun observeCachedNodeIds(): Flow<Set<String>> = unlockedNodesFlow
}
