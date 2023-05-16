package com.lightspark.sdk.core.requester

import com.chrynan.krypt.csprng.SecureRandom
import com.lightspark.sdk.core.LightsparkCoreConfig
import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.core.auth.AuthProvider
import com.lightspark.sdk.core.auth.BETA_HEADER_KEY
import com.lightspark.sdk.core.auth.BETA_HEADER_VALUE
import com.lightspark.sdk.core.crypto.NodeKeyCache
import com.lightspark.sdk.core.crypto.signPayload
import com.lightspark.sdk.core.crypto.signUsingAlias
import com.lightspark.sdk.core.util.getPlatform
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.hours
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
import saschpe.kase64.base64Encoded

private const val DEFAULT_BASE_URL = "api.lightspark.com"

class Requester constructor(
    private val nodeKeyCache: NodeKeyCache,
    private val authProvider: AuthProvider,
    private val jsonSerialFormat: Json,
    private val schemaEndpoint: String,
    private val baseUrl: String = DEFAULT_BASE_URL,
) {
    private val httpClient = HttpClient()
    private val userAgent =
        "lightspark-kotlin-sdk/${LightsparkCoreConfig.VERSION} ${getPlatform().platformName}/${getPlatform().version}"
    private val defaultHeaders = mapOf(
        "Content-Type" to "application/json",
        BETA_HEADER_KEY to BETA_HEADER_VALUE,
        "User-Agent" to userAgent,
        "X-Lightspark-SDK" to userAgent,
    )
    private val secureRandom = SecureRandom()

    @Throws(LightsparkException::class, CancellationException::class)
    suspend fun <T> executeQuery(query: Query<T>): T {
        val response =
            makeRawRequest(
                query.queryPayload,
                variables(jsonSerialFormat, query.variableBuilder),
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
        var bodyData = buildJsonObject {
            put("query", JsonPrimitive(queryPayload))
            variables?.let { put("variables", it) }
            put("operationName", JsonPrimitive(operation))
        }
        var headers = defaultHeaders + authProvider.getCredentialHeaders()
        val signedBodyAndHeaders = try {
            addSigningDataIfNeeded(
                bodyData,
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
        bodyData = signedBodyAndHeaders.first
        headers = signedBodyAndHeaders.second

        val response = httpClient.post("https://$baseUrl/$schemaEndpoint") {
            setBody(bodyData.toString())
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
        val responseData = responseJson["data"]
        if (responseData == null || responseData is JsonNull) {
            val errorArray = responseJson["errors"]?.jsonArray

            if (errorArray == null || errorArray.size == 0) {
                throw LightsparkException(
                    "Request $operation failed. Empty response and error array.",
                    LightsparkErrorCode.REQUEST_FAILED,
                )
            }
            val firstError = errorArray[0].jsonObject
            throw LightsparkException(
                "Request $operation failed. ${Json.encodeToString(responseJson["errors"])}",
                firstError["extensions"]?.jsonObject?.get("error_name")?.jsonPrimitive?.content
                    ?: LightsparkErrorCode.REQUEST_FAILED.name,
            )
        }
        return responseData.jsonObject
    }

    private fun addSigningDataIfNeeded(
        bodyData: JsonObject,
        headers: Map<String, String>,
        signingNodeId: String?,
    ): Pair<JsonObject, Map<String, String>> {
        if (signingNodeId == null) {
            return bodyData to headers
        }

        val newBodyData = bodyData.toMutableMap().apply {
            // Note: The nonce is a 64-bit unsigned integer, but the Kotlin random number generator wants to
            // spit out a signed int, which the backend can't decode.
            put("nonce", JsonPrimitive(secureRandom.nextBits(32).toUInt().toLong()))
            put("expires_at", JsonPrimitive(anHourFromNowISOString()))
        }.let { JsonObject(it) }
        val newBodyString = Json.encodeToString(newBodyData)
        val signature = if (nodeKeyCache.containsAlias(signingNodeId)) {
            signUsingAlias(newBodyString.encodeToByteArray(), nodeKeyCache.getAlias(signingNodeId))
        } else {
            signPayload(newBodyString.encodeToByteArray(), nodeKeyCache[signingNodeId])
        }
        val newHeaders = headers.toMutableMap().apply {
            this["X-LIGHTSPARK-SIGNING"] =
                "{\"v\":1,\"signature\":\"${signature.base64Encoded}\"}"
        }
        return newBodyData to newHeaders
    }

    private fun anHourFromNowISOString() = Clock.System.now().plus(1.hours).toString()
}
