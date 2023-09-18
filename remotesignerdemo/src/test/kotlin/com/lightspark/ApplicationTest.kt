package com.lightspark

import com.lightspark.plugins.configureHTTP
import com.lightspark.plugins.configureRouting
import com.lightspark.sdk.crypto.Secp256k1
import com.lightspark.sdk.uma.InMemoryPublicKeyCache
import com.lightspark.sdk.uma.KtorUmaRequester
import com.lightspark.sdk.uma.KycStatus
import com.lightspark.sdk.uma.LnurlpResponse
import com.lightspark.sdk.uma.PayReqResponse
import com.lightspark.sdk.uma.UMA_VERSION_STRING
import com.lightspark.sdk.uma.UmaProtocolHelper
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

@OptIn(ExperimentalStdlibApi::class)
class ApplicationTest {
    val env = RemoteSigningConfig.fromEnv()

    @Test
    fun testPingRequest() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        application {
            configureHTTP()
            configureRouting(env)
        }
        client.get("/ping").apply {
            assertEquals(HttpStatusCode.NoContent, status)
        }
    }
}
