package com.lightspark.sdk.uma

interface PublicKeyCache {
    fun getPublicKeysForVasp(vaspDomain: String): PubKeyResponse?

    fun addPublicKeysForVasp(vaspDomain: String, pubKeyResponse: PubKeyResponse)

    fun removePublicKeysForVasp(vaspDomain: String)

    fun clear()
}

class InMemoryPublicKeyCache : PublicKeyCache {
    private val cache = mutableMapOf<String, PubKeyResponse>()

    override fun getPublicKeysForVasp(vaspDomain: String): PubKeyResponse? {
        return cache[vaspDomain]
    }

    override fun addPublicKeysForVasp(vaspDomain: String, pubKeyResponse: PubKeyResponse) {
        cache[vaspDomain] = pubKeyResponse
    }

    override fun removePublicKeysForVasp(vaspDomain: String) {
        cache.remove(vaspDomain)
    }

    override fun clear() {
        cache.clear()
    }
}
