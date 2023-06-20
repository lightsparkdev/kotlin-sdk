@file:OptIn(ExperimentalUnsignedTypes::class)

package com.lightspark.sdk.crypto

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

    fun ecdh(seedBytes: ByteArray, derivationPath: String, otherPubKey: String): ByteArray {
        val signer = LightsparkSigner()
        var seed: Seed? = null
        try {
            seed = Seed(seedBytes.toUByteArray().toList())
            return signer.ecdh(seed, derivationPath, otherPubKey).toUByteArray().toByteArray()
        } finally {
            signer.close()
            seed?.close()
        }
    }

    fun derivePublicKey(seedBytes: ByteArray, derivationPath: String): String {
        val signer = LightsparkSigner()
        var seed: Seed? = null
        try {
            seed = Seed(seedBytes.toUByteArray().toList())
            return signer.derivePublicKey(seed, derivationPath)
        } finally {
            signer.close()
            seed?.close()
        }
    }

    @JvmOverloads
    fun signMessage(
        message: ByteArray,
        seedBytes: ByteArray,
        derivationPath: String? = null,
        multTweak: ByteArray? = null,
        addTweak: ByteArray? = null,
    ): ByteArray {
        val signer = LightsparkSigner()
        var seed: Seed? = null
        try {
            seed = Seed(seedBytes.toUByteArray().toList())
            val signature = signer.deriveKeyAndSign(
                seed,
                message = message.toUByteArray().toList(),
                derivationPath = derivationPath ?: "",
                mulTweak = multTweak?.toUByteArray()?.toList(),
                addTweak = addTweak?.toUByteArray()?.toList(),
            )
            return  signature.toUByteArray().toByteArray()
        } finally {
            signer.close()
            seed?.close()
        }
    }
}
