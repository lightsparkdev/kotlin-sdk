package com.lightspark.sdk.core.crypto

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NodeKeyCache {
    private val keyCache = mutableMapOf<String, ByteArray>()
    private val keyAliasCache = mutableMapOf<String, String>()
    private val unlockedNodesFlow = MutableStateFlow<Set<String>>(emptySet())

    fun contains(nodeId: String): Boolean {
        return keyCache.containsKey(nodeId) || keyAliasCache.containsKey(nodeId)
    }

    operator fun get(nodeId: String): ByteArray {
        return keyCache[nodeId] ?: throw MissingKeyException(nodeId)
    }

    operator fun set(nodeId: String, key: ByteArray) {
        keyCache[nodeId] = key
        onCachedKeysChanged()
    }

    private fun onCachedKeysChanged() {
        val didWork = unlockedNodesFlow.tryEmit(keyCache.keys + keyAliasCache.keys)
        if (!didWork) {
            unlockedNodesFlow.value = keyCache.keys + keyAliasCache.keys
        }
    }

    fun containsAlias(nodeId: String): Boolean {
        return keyAliasCache.containsKey(nodeId)
    }

    fun getAlias(nodeId: String): String {
        return keyAliasCache[nodeId] ?: throw MissingKeyException(nodeId)
    }

    fun setAlias(nodeId: String, alias: String) {
        keyAliasCache[nodeId] = alias
        onCachedKeysChanged()
    }

    fun remove(nodeId: String) {
        keyCache.remove(nodeId)
        onCachedKeysChanged()
    }

    fun clear() {
        keyCache.clear()
        keyAliasCache.clear()
        onCachedKeysChanged()
    }

    fun observeCachedNodeIds(): Flow<Set<String>> = unlockedNodesFlow
}
