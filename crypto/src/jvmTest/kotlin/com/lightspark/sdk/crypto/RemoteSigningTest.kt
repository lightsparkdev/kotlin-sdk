package com.lightspark.sdk.crypto

import com.lightspark.sdk.crypto.internal.Network
import fr.acinq.secp256k1.Secp256k1
import saschpe.kase64.base64DecodedBytes
import saschpe.kase64.base64Encoded
import java.security.MessageDigest
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteSigningTest {
    @Test
    fun testGetMnemonicSeedPhrase() {
        val entropy = "geVgqn+RALV+fPe1fvra9SNotfA/e2BprRqu2ub/6wg=".base64DecodedBytes
        val result = RemoteSigning.getMnemonicSeedPhrase(entropy)
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
    fun testDeriveKeyWithDerivationPathButNoTweaks() {
        val privateKeySeed = (
            "fffcf9f6f3f0edeae7e4e1dedbd8d5d2cfccc9c6c3c0bdbab7b4b1aeaba8a5a29f9c999693908d8a878481" +
                "7e7b7875726f6c696663605d5a5754514e4b484542"
            ).hexAsByteArray()
        val derivationPath = "m/0/2147483647'/1"
        val result = RemoteSigning.derivePublicKey(privateKeySeed, Network.BITCOIN, derivationPath)
        assertEquals(
            "xpub6DF8uhdarytz3FWdA8TvFSvvAh8dP3283MY7p2V4SeE2wyWmG5mg5EwVvmdMVCQcoNJxGoWaU9DCWh89LojfZ537wTf" +
                "unKau47EL2dhHKon",
            result,
        )
    }

    @Test
    fun testSigningWithDerivedKey() {
        val message = "Hello Crypto World".toByteArray().sha256()
        println(message.map { it.toInt() })
        val privateKeySeed = (
            "fffcf9f6f3f0edeae7e4e1dedbd8d5d2cfccc9c6c3c0bdbab7b4b1aeaba8a5a29f9c999693908d8a878481" +
                "7e7b7875726f6c696663605d5a5754514e4b484542"
            ).hexAsByteArray()
        val derivationPath = "m/0/2147483647'/1"
        val result = RemoteSigning.signMessage(message, privateKeySeed, Network.BITCOIN, derivationPath)
        println(result.map { it.toInt() })
        val expectedSig = "fagpGOb9o/E8g62yL6jV5wtpTVzJ7R4rh0Xt2Uw4fPVd1Q+2ZJbkSrRBRj0bvk1qTSiCvoiCfD5CMEHZL4fAlA=="
        println(expectedSig.base64DecodedBytes.map { it.toInt() })
        assertEquals(expectedSig, result.base64Encoded)
    }

    @Test
    fun testDeriveRevocationSecretAndSign() {
        val message = "Hello Crypto World".toByteArray().sha256()
        // Values from:
        // https://github.com/lightning/bolts/blob/master/03-transactions.md#appendix-e-key-derivation-test-vectors
        val seed = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f".hexAsByteArray()
        val perCommitmentSecret = "1f1e1d1c1b1a191817161514131211100f0e0d0c0b0a09080706050403020100".hexAsByteArray()
        val basePoint = "036d6caac248af96f6afa7f904f550253a0f3ef3f5aa2fe6838a95b216691468e2".hexAsByteArray()
        val perCommitmentPoint = "025f7117a78150fe2ef97db7cfc83bd57b2e2c0d0dd25eaf467a4a1c2a45ce1486".hexAsByteArray()
        val h1 = (basePoint + perCommitmentPoint).sha256()
        val h2 = (perCommitmentPoint + basePoint).sha256()
        val addTweak = Secp256k1.privKeyTweakMul(perCommitmentSecret, h2)
        val result = RemoteSigning.signMessage(
            message,
            seed,
            Network.BITCOIN,
            derivationPath = "m",
            addTweak = addTweak,
            multTweak = h1,
        )
        val expectedSignatureBase64 =
            "ZIp/flF8rVliQn96we+12AzWcNX2QxRN1Ma5FGv1YQVMPt9ylLfcGs0knd33jHKuOjHOD7TIkFEoKMelSi9eMA=="
        assertEquals(expectedSignatureBase64, result.base64Encoded)
    }

    @Test
    fun testEcdh() {
        val seed1HexString = "000102030405060708090a0b0c0d0e0f";
        val seed1Bytes = seed1HexString.hexAsByteArray()

        val seed2HexString = "fffcf9f6f3f0edeae7e4e1dedbd8d5d2cfccc9c6c3c0bdbab7b4b1aeaba8a5a29f9c999693908d8a8784817e7b7875726f6c696663605d5a5754514e4b484542";
        val seed2Bytes = seed2HexString.hexAsByteArray()

        val pub1 = "027c4b09ffb985c298afe7e5813266cbfcb7780b480ac294b0b43dc21f2be3d13c"
        val pub2 = "02fc9e5af0ac8d9b3cecfe2a888e2117ba3d089d8585886c9c826b6b22a98d12ea"

        val secret1 = RemoteSigning.ecdh(seed1Bytes, Network.BITCOIN, pub2)
        val secret2 = RemoteSigning.ecdh(seed2Bytes, Network.BITCOIN, pub1)
        assertEquals(secret1.toHex(), secret2.toHex())
    }

    private fun String.hexAsByteArray(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        val byteIterator = chunkedSequence(2)
            .map { it.toInt(16).toByte() }
            .iterator()

        return ByteArray(length / 2) { byteIterator.next() }
    }

    fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

    private fun ByteArray.sha256(): ByteArray {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(this)
        return md.digest()
    }
}
