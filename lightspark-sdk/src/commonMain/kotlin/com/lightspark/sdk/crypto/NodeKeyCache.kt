package com.lightspark.sdk.crypto

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class NodeKeyCache {
    private val keyCache = mutableMapOf<String, ByteArray>()
    private val unlockedNodesFlow = MutableStateFlow<Set<String>>(emptySet())

    fun contains(nodeId: String): Boolean {
        return keyCache.containsKey(nodeId)
    }

    operator fun get(nodeId: String): ByteArray {
        return keyCache[nodeId] ?: throw MissingKeyException(nodeId)
    }

    operator fun set(nodeId: String, key: ByteArray) {
        keyCache[nodeId] = key
        val didWork = unlockedNodesFlow.tryEmit(keyCache.keys)
        if (!didWork) {
            unlockedNodesFlow.value = keyCache.keys
        }
    }

    fun safeGetKey(nodeId: String): ByteArray? {
        return keyCache[nodeId]
    }

    fun remove(nodeId: String) {
        keyCache.remove(nodeId)
        unlockedNodesFlow.tryEmit(keyCache.keys)
    }

    fun clear() {
        keyCache.clear()
        unlockedNodesFlow.tryEmit(emptySet())
    }

    fun observeCachedNodeIds(): Flow<Set<String>> = unlockedNodesFlow
}