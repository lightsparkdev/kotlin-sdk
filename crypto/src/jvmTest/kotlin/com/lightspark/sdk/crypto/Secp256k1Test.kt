package com.lightspark.sdk.crypto

import com.lightspark.sdk.crypto.internal.hexToByteArray
import kotlin.test.Test
import kotlin.test.assertTrue

private const val PUB_KEY_HEX = "048bddd1bfa96664257f88ff55ac64f58066501220584f28d484e7919715a64545fd0ef6ca64336166eba4f16079271e43e387d9706675406273b205dcb1c502c9"
private const val PRIV_KEY_HEX = "647a852f8f59a4037af2cfed9e00f9bbfe6a75b95aea73cdd0a4a5ab3141fbd7"

class Secp256k1Test {
    @Test
    fun testEcdsa() {
        val message = "hello world".toByteArray()
        val signature = Secp256k1.signEcdsa(message, PRIV_KEY_HEX.hexToByteArray())
        val verified = Secp256k1.verifyEcdsa(message, signature, PUB_KEY_HEX.hexToByteArray())
        assertTrue(verified)
    }

    @Test
    fun testEcies() {
        val message = "hello world".toByteArray()
        val encrypted = Secp256k1.encryptEcies(message, PUB_KEY_HEX.hexToByteArray())
        val decrypted = Secp256k1.decryptEcies(encrypted, PRIV_KEY_HEX.hexToByteArray())
        assertTrue(message.contentEquals(decrypted))
    }
}
