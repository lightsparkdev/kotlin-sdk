package com.lightspark.sdk.core.requester

import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.core.LightsparkCoreConfig
import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.core.auth.AuthProvider
import com.lightspark.sdk.core.crypto.MissingKeyException
import com.lightspark.sdk.core.crypto.NodeKeyCache
import com.lightspark.sdk.core.crypto.nextLong
import com.lightspark.sdk.core.util.getPlatform
import io.ktor.client.HttpClient
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import saschpe.kase64.base64Encoded
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.hours
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.zip.Deflater

private const val DEFAULT_BASE_URL = "api.lightspark.com"

class Requester constructor(
    private val nodeKeyCache: NodeKeyCache,
    private val authProvider: AuthProvider,
    private val jsonSerialFormat: Json,
    private val schemaEndpoint: String,
    private val baseUrl: String = DEFAULT_BASE_URL,
) {
    private val httpClient = HttpClient {
        install(ContentEncoding) {
            gzip()  // Switch to deflate() when https://youtrack.jetbrains.com/issue/KTOR-6999 is fixed
        }
        install(WebSockets)
    }
    private val userAgent =
        "lightspark-kotlin-sdk/${LightsparkCoreConfig.VERSION} ${getPlatform().platformName}/${getPlatform().version}"
    private val defaultHeaders = mapOf(
        "Content-Type" to "application/json",
        "User-Agent" to userAgent,
        "X-Lightspark-SDK" to userAgent,
    )
    private var websocketConnectionHandler: WebsocketConnectionHandler? = null

    @Throws(LightsparkException::class, CancellationException::class)
    suspend fun <T> executeQuery(query: Query<T>): T {
        val response =
            makeRawRequest(
                query.queryPayload,
                buildJsonObject(jsonSerialFormat, query.variableBuilder),
                query.signingNodeId,
            )
        return query.deserializer(response)
    }

    @Throws(LightsparkException::class, CancellationException::class)
    suspend fun makeRawRequest(
        queryPayload: String,
        variables: JsonObject? = null,
        signingNodeId: String? = null,
    ): JsonObject {
        val operationNameRegex =
            Regex("^\\s*(query|mutation|subscription)\\s+(\\w+)", RegexOption.IGNORE_CASE)
        val operationMatch = operationNameRegex.find(queryPayload)
        if (operationMatch == null || operationMatch.groupValues.size < 3) {
            throw LightsparkException("Invalid query payload", LightsparkErrorCode.INVALID_QUERY)
        }
        val operationType = operationMatch.groupValues[1]
        if (operationType == "subscription") {
            throw LightsparkException(
                "Subscription queries should call subscribe instead",
                LightsparkErrorCode.INVALID_QUERY,
            )
        }
        val operation = operationMatch.groupValues[2]
        var payload = buildJsonObject {
            put("query", JsonPrimitive(queryPayload))
            variables?.let { put("variables", it) }
            put("operationName", JsonPrimitive(operation))
        }
        var headers = defaultHeaders + authProvider.getCredentialHeaders() + mapOf("X-GraphQL-Operation" to operation)
        val signedBodyAndHeaders = try {
            addSigningDataIfNeeded(
                payload,
                headers,
                signingNodeId,
            )
        } catch (e: Exception) {
            throw LightsparkException(
                "Failed to sign request",
                LightsparkErrorCode.SIGNING_FAILED,
                e,
            )
        }
        var body = signedBodyAndHeaders.first
        headers = signedBodyAndHeaders.second

        if (body.size > 1024) {
            val output = ByteArray(body.size)
            val deflater = Deflater(Deflater.BEST_SPEED)
            deflater.setInput(body)
            deflater.finish()
            val length = deflater.deflate(output)
            deflater.end()
            body = output.copyOfRange(0, length)

            headers = headers.toMutableMap().apply {
                this["Content-Encoding"] = "deflate"
            }
        }

        val response = httpClient.post("https://$baseUrl/$schemaEndpoint") {
            setBody(body)
            headers {
                headers.forEach { (key, value) ->
                    append(key, value)
                }
            }
        }
        if (!response.status.isSuccess()) {
            throw LightsparkException(
                "Request $operation failed. ${response.status}",
                "RequestFailed",
            )
        }
        val responseJson =
            response.bodyAsText().let { Json.decodeFromString(JsonObject.serializer(), it) }
        val errors = responseJson["errors"]?.jsonArray
        if (errors != null && errors.size > 0) {
            val firstError = errors[0].jsonObject
            throw LightsparkException(
                "Request $operation failed. ${Json.encodeToString(responseJson["errors"])}",
                firstError["extensions"]?.jsonObject?.get("error_name")?.jsonPrimitive?.content
                    ?: LightsparkErrorCode.REQUEST_FAILED.name,
            )
        }
        val responseData = responseJson["data"]
        if (responseData == null || responseData is JsonNull) {
            throw LightsparkException(
                "Request $operation failed. Empty response and error array.",
                LightsparkErrorCode.REQUEST_FAILED,
            )
        }
        return responseData.jsonObject
    }

    @Throws(LightsparkException::class, CancellationException::class)
    inline fun <reified T> executeAsSubscription(query: Query<T>): Flow<Lce<T>> {
        return makeRawSubscription(query).map { response ->
            try {
                when (response) {
                    is Lce.Error -> throw response.exception ?: LightsparkException(
                        "Unknown error",
                        LightsparkErrorCode.UNKNOWN,
                    )

                    is Lce.Loading -> Lce.Loading
                    is Lce.Content -> {
                        val dataJson = response.data["data"] ?: throw LightsparkException(
                            "Invalid response",
                            LightsparkErrorCode.REQUEST_FAILED,
                        )
                        Lce.Content(query.deserializer(dataJson.jsonObject))
                    }
                }
            } catch (e: Exception) {
                Lce.Error(e)
            }
        }
    }

    @Throws(LightsparkException::class, CancellationException::class)
    fun makeRawSubscription(
        query: Query<*>,
    ): Flow<Lce<JsonObject>> {
        if (websocketConnectionHandler == null) {
            websocketConnectionHandler = WebsocketConnectionHandler(
                httpClient,
                "wss://$baseUrl/$schemaEndpoint",
                jsonSerialFormat,
                {
                    buildJsonObject(jsonSerialFormat) {
                        authProvider.withValidAuthToken { add("access_token", it) }
                    }
                },
                defaultHeaders,
            )
        }
        return websocketConnectionHandler!!.execute(query)
    }

    private fun addSigningDataIfNeeded(
        bodyData: JsonObject,
        headers: Map<String, String>,
        signingNodeId: String?,
    ): Pair<ByteArray, Map<String, String>> {
        if (signingNodeId == null) {
            return bodyData.toString().encodeToByteArray() to headers
        }
        if (!nodeKeyCache.contains(signingNodeId)) {
            throw MissingKeyException(signingNodeId)
        }

        val newBodyData = bodyData.toMutableMap().apply {
            // Note: The nonce is a 64-bit unsigned integer, but the Kotlin random number generator wants to
            // spit out a signed int, which the backend can't decode, so we take the absolute value.
            put("nonce", JsonPrimitive(nextLong().absoluteValue))
            put("expires_at", JsonPrimitive(anHourFromNowISOString()))
        }.let { JsonObject(it) }
        val newBodyString = newBodyData.toString().encodeToByteArray()
        val signature = nodeKeyCache[signingNodeId].sign(newBodyString)
        val newHeaders = headers.toMutableMap().apply {
            this["X-LIGHTSPARK-SIGNING"] =
                "{\"v\":1,\"signature\":\"${signature.base64Encoded}\"}"
        }
        return newBodyString to newHeaders
    }

    private fun anHourFromNowISOString() = Clock.System.now().plus(1.hours).toString()
}
