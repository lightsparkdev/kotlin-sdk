package com.lightspark.sdk.crypto

import fr.acinq.bitcoin.DeterministicWallet
import fr.acinq.bitcoin.crypto.Digest
import fr.acinq.secp256k1.Secp256k1
import saschpe.kase64.base64DecodedBytes
import saschpe.kase64.base64Encoded
import kotlin.math.exp
import kotlin.test.Test
import kotlin.test.assertEquals

class SigningTest {
    @Test
    fun testGetMnemonicSeedPhrase() {
        val entropy = "geVgqn+RALV+fPe1fvra9SNotfA/e2BprRqu2ub/6wg=".base64DecodedBytes
        val result = Signing.getMnemonicSeedPhrase(entropy)
        val expected = listOf(
            "limit",
            "climb",
            "clever",
            "you",
            "avoid",
            "follow",
            "wheat",
            "page",
            "rely",
            "water",
            "repeat",
            "tumble",
            "custom",
            "foot",
            "science",
            "urge",
            "gather",
            "estate",
            "effort",
            "frozen",
            "purpose",
            "lend",
            "promote",
            "anchor",
        )
        assertEquals(expected, result)
    }

    @Test
    fun `test derive key with derivation path but no tweaks`() {
        val privateKeySeed = "fffcf9f6f3f0edeae7e4e1dedbd8d5d2cfccc9c6c3c0bdbab7b4b1aeaba8a5a29f9c999693908d8a8784817e7b7875726f6c696663605d5a5754514e4b484542".hexAsByteArray()
        val derivationPath = "m/0/2147483647'/1"
        val result = Signing.deriveKey(privateKeySeed, derivationPath)
        assertEquals(
            "xprv9zFnWC6h2cLgpmSA46vutJzBcfJ8yaJGg8cX1e5StJh45BBciYTRXSd25UEPVuesF9yog62tGAQtHjXajPPdbRCHuWS6T8XA2ECKADdw4Ef",
            DeterministicWallet.encode(result, false),
        )
        assertEquals(
            "xpub6DF8uhdarytz3FWdA8TvFSvvAh8dP3283MY7p2V4SeE2wyWmG5mg5EwVvmdMVCQcoNJxGoWaU9DCWh89LojfZ537wTfunKau47EL2dhHKon",
            DeterministicWallet.encode(DeterministicWallet.publicKey(result), false),
        )
    }

    @Test
    fun `test signing with derived key`() {
        val message = Digest.sha256().hash("Hello Crypto World".toByteArray())
        val privateKeySeed = "fffcf9f6f3f0edeae7e4e1dedbd8d5d2cfccc9c6c3c0bdbab7b4b1aeaba8a5a29f9c999693908d8a8784817e7b7875726f6c696663605d5a5754514e4b484542".hexAsByteArray()
        val derivationPath = "m/0/2147483647'/1"
        val result = Signing.signMessage(message, privateKeySeed, derivationPath)
        val expectedSig = "/UJfF9wsDtwkUAcnnJcEmvINBSMbwawWZNmmDy0DXFMtO76NV9AHGlSvYzvZjcxqympNWSqp4mYwebQXBrrw5A=="
        assertEquals(expectedSig, result.base64Encoded)
    }

    @Test
    fun `test derive revocation secret and sign`() {
        val message = Digest.sha256().hash("Hello Crypto World".toByteArray())
        // Values from https://github.com/lightning/bolts/blob/master/03-transactions.md#appendix-e-key-derivation-test-vectors
        val seed = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f".hexAsByteArray()
        val perCommitmentSecret = "1f1e1d1c1b1a191817161514131211100f0e0d0c0b0a09080706050403020100".hexAsByteArray()
        val basePoint = "036d6caac248af96f6afa7f904f550253a0f3ef3f5aa2fe6838a95b216691468e2".hexAsByteArray()
        val perCommitmentPoint = "025f7117a78150fe2ef97db7cfc83bd57b2e2c0d0dd25eaf467a4a1c2a45ce1486".hexAsByteArray()
        val h1 = Digest.sha256().hash(basePoint + perCommitmentPoint)
        val h2 = Digest.sha256().hash(perCommitmentPoint + basePoint)
        val addTweak = Secp256k1.privKeyTweakMul(perCommitmentSecret, h2)
        val result = Signing.signMessage(
            message,
            seed,
            derivationPath = null,
            addTweak = addTweak,
            multTweak = h1
        )
        val expectedSignatureBase64 =
            "/qYwtDRunSIxYdwVT1U6Qr81Mp37zJUYlIRWohi08bIU10J4U5ERTrDv1jaR+7kjtQyovrM0tEUTrDw4M90sQA=="
        assertEquals(expectedSignatureBase64, result.base64Encoded)
    }

    private fun String.hexAsByteArray(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        val byteIterator = chunkedSequence(2)
            .map { it.toInt(16).toByte() }
            .iterator()

        return ByteArray(length / 2) { byteIterator.next() }
    }

    fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
}
