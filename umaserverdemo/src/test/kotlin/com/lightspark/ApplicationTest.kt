package com.lightspark

import com.lightspark.plugins.configureHTTP
import com.lightspark.plugins.configureRouting
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import me.uma.InMemoryPublicKeyCache
import me.uma.KtorUmaRequester
import me.uma.UMA_VERSION_STRING
import me.uma.UmaProtocolHelper
import me.uma.crypto.Secp256k1
import me.uma.protocol.KycStatus
import me.uma.protocol.LnurlpResponse
import me.uma.protocol.PayReqResponse
import me.uma.protocol.compliance

@OptIn(ExperimentalStdlibApi::class)
class ApplicationTest {
    val env = UmaConfig.fromEnv()

    @Test
    fun testLnurlpRequest() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        application {
            configureHTTP()
            configureRouting(env, UmaProtocolHelper(InMemoryPublicKeyCache(), KtorUmaRequester(client)))
        }
        val uma = UmaProtocolHelper(InMemoryPublicKeyCache(), KtorUmaRequester(client))
        val requestUrlString = uma.getSignedLnurlpRequestUrl(
            env.umaSigningPrivKey,
            "\$bob@localhost",
            "localhost",
            false,
        )
        client.get(requestUrlString).apply {
            assertEquals(HttpStatusCode.OK, status)
            val response = body<LnurlpResponse>()
            assertEquals(response.umaVersion, UMA_VERSION_STRING)
            assertNotNull(response.requiredPayerData?.get("compliance"))
            assertTrue(response.requiredPayerData?.get("compliance")!!.mandatory)
        }
    }

    @Test
    fun testPayRequest() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        application {
            configureHTTP()
            configureRouting(env, UmaProtocolHelper(InMemoryPublicKeyCache(), KtorUmaRequester(client)))
        }
        val uma = UmaProtocolHelper(InMemoryPublicKeyCache(), KtorUmaRequester(client))
        val trInfo = "some fake travel rule info"
        val payRequest = uma.getPayRequest(
            env.umaEncryptionPubKey,
            env.umaSigningPrivKey,
            "USD",
            100L,
            true,
            "\$alice@localhost",
            KycStatus.VERIFIED,
            "localhost/utxocallback",
            trInfo,
            payerNodePubKey = "abcdef",
        )
        val decryptedTrInfo = Secp256k1.decryptEcies(
            payRequest.payerData!!.compliance()!!.encryptedTravelRuleInfo!!.hexToByteArray(),
            env.umaEncryptionPrivKey,
        )
        assertEquals(trInfo, decryptedTrInfo.decodeToString())
        client.post("http://localhost/api/uma/payreq/${env.userID}") {
            setBody(payRequest)
            contentType(io.ktor.http.ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val response = body<PayReqResponse>()
            assertEquals("USD", response.paymentInfo!!.currencyCode)
            assertNotNull(response.encodedInvoice)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testLnurlpubKey() = testApplication {
        application {
            configureHTTP()
            configureRouting(
                env,
                UmaProtocolHelper(InMemoryPublicKeyCache(), KtorUmaRequester(this@testApplication.client)),
            )
        }
        val uma = UmaProtocolHelper(InMemoryPublicKeyCache(), KtorUmaRequester(client))
        val pubKeyResponse = uma.fetchPublicKeysForVasp("localhost:80")
        assertEquals(env.umaSigningPubKeyHex, pubKeyResponse.getSigningPublicKey().toHexString())
    }
}
