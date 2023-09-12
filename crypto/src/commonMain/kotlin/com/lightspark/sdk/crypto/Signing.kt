@file:OptIn(ExperimentalUnsignedTypes::class)

package com.lightspark.sdk.crypto

import com.lightspark.sdk.crypto.internal.LightsparkSigner
import com.lightspark.sdk.crypto.internal.Mnemonic
import com.lightspark.sdk.crypto.internal.Network
import com.lightspark.sdk.crypto.internal.Seed
import com.lightspark.sdk.crypto.internal.use

object Signing {
    fun getMnemonicSeedPhrase(entropy: ByteArray): List<String> =
        Mnemonic.fromEntropy(entropy.map { it.toUByte() }).use {
            it.asString().split(" ")
        }

    fun mnemonicToSeed(mnemonic: List<String>): ByteArray =
        Mnemonic.fromPhrase(mnemonic.joinToString(" ")).use {
            Seed.fromMnemonic(it).use { seed ->
                seed.asBytes().toUByteArray().toByteArray()
            }
        }

    fun ecdh(seedBytes: ByteArray, network: Network, otherPubKeyHex: String): ByteArray {
        return withSeedAndSigner(seedBytes, network) { _, signer ->
            val otherPubKeyBytes = otherPubKeyHex.hexToByteArray().toUByteArray().toList()
            signer.ecdh(otherPubKeyBytes).toUByteArray().toByteArray()
        }
    }

    fun derivePublicKey(seedBytes: ByteArray, network: Network, derivationPath: String): String {
        return withSeedAndSigner(seedBytes, network) { _, signer ->
            signer.derivePublicKey(derivationPath)
        }
    }

    @JvmOverloads
    fun signMessage(
        message: ByteArray,
        seedBytes: ByteArray,
        network: Network,
        derivationPath: String? = null,
        isRaw: Boolean = false,
        multTweak: ByteArray? = null,
        addTweak: ByteArray? = null,
    ): ByteArray {
        return withSeedAndSigner(seedBytes, network) { _, signer ->
            signer.deriveKeyAndSign(
                message = message.toUByteArray().toList(),
                derivationPath = derivationPath ?: "",
                isRaw = isRaw,
                mulTweak = multTweak?.toUByteArray()?.toList(),
                addTweak = addTweak?.toUByteArray()?.toList(),
            ).toUByteArray().toByteArray()
        }
    }

    private fun <T> withSeedAndSigner(seedBytes: ByteArray, network: Network, block: (Seed, LightsparkSigner) -> T): T {
        var seed: Seed? = null
        var signer: LightsparkSigner? = null
        try {
            seed = Seed(seedBytes.toUByteArray().toList())
            signer = LightsparkSigner(seed, network)
            return block(seed, signer)
        } finally {
            signer?.close()
            seed?.close()
        }
    }
}

private fun String.hexToByteArray(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }

    val byteIterator = chunkedSequence(2)
        .map { it.toInt(16).toByte() }
        .iterator()

    return ByteArray(length / 2) { byteIterator.next() }
}
