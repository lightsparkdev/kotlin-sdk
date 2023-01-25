package com.lightspark.sdk.crypto

import com.apollographql.apollo3.api.http.HttpBody
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.chrynan.krypt.csprng.SecureRandom
import com.lightspark.sdk.util.format
import kotlinx.datetime.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import okio.Buffer
import okio.BufferedSink
import saschpe.kase64.base64Encoded

internal class SigningHttpInterceptor(private val nodeKeyCache: NodeKeyCache) : HttpInterceptor {
    private val secureRandom = SecureRandom()

    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        val body = request.body ?: return chain.proceed(request)
        val bodyString = Buffer().apply { body.writeTo(this) }.readUtf8()
        val bodyAsMap = Json.parseToJsonElement(bodyString).jsonObject
        val nodeId = request.headers.get("X-Lightspark-node-id") ?: return chain.proceed(request)
        val nodeKey = nodeKeyCache[nodeId]

        val newBodyString = bodyAsMap.toMutableMap().apply {
            // Note: The nonce is a 64-bit unsigned integer, but the Kotlin random number generator wants to
            // spit out a signed int, which the backend can't decode.
            put("nonce", JsonPrimitive(secureRandom.nextBits(32).toUInt().toLong()))
            put("expires_at", JsonPrimitive(anHourFromNowISOString()))
        }.let { Json.encodeToString(JsonObject(it)) }
        val signature = signPayload(newBodyString.encodeToByteArray(), nodeKey)
        val signedRequest = request.newBuilder().apply {
            body(newBodyString.toHttpBody())
            headers(request.headers.associate { it.name to it.value }.toMutableMap().apply {
                remove("X-Lightspark-node-id")
                this["X-LIGHTSPARK-SIGNING"] =
                    "{\"v\":1,\"signature\":\"${signature.base64Encoded}\"}"
            }.map { HttpHeader(it.key, it.value) })
        }.build()

        return chain.proceed(signedRequest)
    }

    private fun anHourFromNowISOString() =
        (Clock.System.now().plus(DateTimePeriod(hours = 1), TimeZone.UTC))
            .toLocalDateTime(TimeZone.UTC)
            .format("yyyy-MM-dd'T'HH:mm:ss'Z'")
}

fun String.toHttpBody() = object : HttpBody {
    override val contentLength = this@toHttpBody.length.toLong()
    override val contentType = "application/json"

    override fun writeTo(bufferedSink: BufferedSink) {
        bufferedSink.writeUtf8(this@toHttpBody)
    }
}

fun List<HttpHeader>.get(name: String): String? {
    return singleOrNull { it.name.lowercase() == name.lowercase() }?.value?.trim()
}
