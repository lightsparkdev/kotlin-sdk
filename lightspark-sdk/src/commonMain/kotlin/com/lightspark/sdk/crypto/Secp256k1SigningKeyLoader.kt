package com.lightspark.sdk.crypto

import com.lightspark.sdk.core.crypto.Secp256k1SigningKey
import com.lightspark.sdk.core.crypto.SigningKey
import com.lightspark.sdk.core.crypto.SigningKeyLoader
import com.lightspark.sdk.core.requester.Requester
import com.lightspark.sdk.crypto.internal.Network
import com.lightspark.sdk.model.BitcoinNetwork

class Secp256k1SigningKeyLoader(
    private val seedBytes: ByteArray,
    private val bitcoinNetwork: BitcoinNetwork,
) : SigningKeyLoader {
    companion object {
        private const val SIGNING_KEY_DERIVATION_PATH = "m/5"
    }

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun loadSigningKey(requester: Requester): SigningKey {
        val keyHex = RemoteSigning.derivePrivateKey(
            seedBytes,
            bitcoinNetwork.toCryptoNetwork(),
            SIGNING_KEY_DERIVATION_PATH,
        )
        return Secp256k1SigningKey(keyHex.hexToByteArray())
    }

    private fun BitcoinNetwork.toCryptoNetwork(): Network {
        return when (this) {
            BitcoinNetwork.MAINNET -> Network.BITCOIN
            BitcoinNetwork.TESTNET -> Network.TESTNET
            BitcoinNetwork.REGTEST -> Network.REGTEST
            else -> Network.BITCOIN
        }
    }
}
