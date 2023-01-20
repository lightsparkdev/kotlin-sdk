package com.lightspark.sdk.crypto

import com.apollographql.apollo3.api.http.HttpBody
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.chrynan.krypt.csprng.SecureRandom
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import okio.Buffer
import okio.BufferedSink
import saschpe.kase64.base64Encoded

internal class SigningHttpInterceptor(private val nodeKeyCache: NodeKeyCache) : HttpInterceptor {
    private val secureRandom = SecureRandom()

    override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
        val body = request.body ?: return chain.proceed(request)
        val bodyString = Buffer().apply { body.writeTo(this) }.readUtf8()
        val bodyAsMap = Json.parseToJsonElement(bodyString).jsonObject
        val nodeId = request.headers.get("X-Lightspark-node-id") ?: return chain.proceed(request)
        val nodeKey = nodeKeyCache[nodeId]

        val signature = signPayload(bodyString.encodeToByteArray(), nodeKey)
        val newBodyString = bodyAsMap.toMutableMap().apply {
            put("nonce", JsonPrimitive(secureRandom.nextBits(32)))
            put("expires_at", JsonPrimitive("2024-09-01T00:00:00Z")) // TODO: 1 hour from now.
        }.let { Json.encodeToString(JsonObject(it)) }
        val signedRequest = request.newBuilder().apply {
            body(newBodyString.toHttpBody())
            headers(request.headers.associate { it.name to it.value }.toMutableMap().apply {
                remove("X-Lightspark-node-id")
                this["X-Lightspark-Signature"] = signature.base64Encoded
            }.map { HttpHeader(it.key, it.value) })
        }.build()

        return chain.proceed(signedRequest)
    }
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
