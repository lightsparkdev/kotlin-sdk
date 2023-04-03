package com.lightspark.sdk.requester

import com.chrynan.krypt.csprng.SecureRandom
import com.lightspark.sdk.LightsparkErrorCode
import com.lightspark.sdk.LightsparkException
import com.lightspark.sdk.auth.AuthProvider
import com.lightspark.sdk.auth.BETA_HEADER_KEY
import com.lightspark.sdk.auth.BETA_HEADER_VALUE
import com.lightspark.sdk.crypto.NodeKeyCache
import com.lightspark.sdk.crypto.signPayload
import com.lightspark.sdk.util.format
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.datetime.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import saschpe.kase64.base64Encoded

private const val DEFAULT_BASE_URL = "api.lightspark.com"

internal class Requester constructor(
    private val nodeKeyCache: NodeKeyCache,
    private val authProvider: AuthProvider,
    private val baseUrl: String = DEFAULT_BASE_URL,
) {
    private val httpClient = HttpClient()
    private val defaultHeaders = mapOf(
        "Content-Type" to "application/json",
        BETA_HEADER_KEY to BETA_HEADER_VALUE,
    )
    private val secureRandom = SecureRandom()

    suspend fun <T> executeQuery(query: Query<T>): T? {
        val response =
            makeRawRequest(
                query.queryPayload,
                variables(query.variableBuilder),
                query.signingNodeId,
            )
        return response?.let { query.deserializer(it) }
    }

    suspend fun makeRawRequest(
        queryPayload: String,
        variables: JsonObject? = null,
        signingNodeId: String? = null,
    ): JsonObject? {
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
        val signedBodyAndHeaders = addSigningDataIfNeeded(
            bodyData,
            headers,
            signingNodeId,
        )
        bodyData = signedBodyAndHeaders.first
        headers = signedBodyAndHeaders.second

        val response = httpClient.post("https://$baseUrl/graphql/release_candidate") {
            setBody(bodyData.toString())
            headers {
                headers.forEach { (key, value) ->
                    append(key, value)
                }
            }
        }
        if (!response.status.isSuccess()) {
            // TODO: Handle error codes.
            // TODO: Use a proper logger.
            print("Request $operation failed. ${response.status}")
            return null
        }
        val responseJson =
            response.bodyAsText().let { Json.decodeFromString(JsonObject.serializer(), it) }
        val data = responseJson["data"]?.jsonObject
        if (data == null) {
            print(
                "Request $operation failed. ${Json.encodeToString(responseJson["errors"] ?: "")}",
            )
            return null
        }
        return data
    }

    private fun addSigningDataIfNeeded(
        bodyData: JsonObject,
        headers: Map<String, String>,
        signingNodeId: String?,
    ): Pair<JsonObject, Map<String, String>> {
        if (signingNodeId == null) {
            return bodyData to headers
        }

        val nodeKey = nodeKeyCache[signingNodeId]

        val newBodyData = bodyData.toMutableMap().apply {
            // Note: The nonce is a 64-bit unsigned integer, but the Kotlin random number generator wants to
            // spit out a signed int, which the backend can't decode.
            put("nonce", JsonPrimitive(secureRandom.nextBits(32).toUInt().toLong()))
            put("expires_at", JsonPrimitive(anHourFromNowISOString()))
        }.let { JsonObject(it) }
        val newBodyString = Json.encodeToString(newBodyData)
        val signature = signPayload(newBodyString.encodeToByteArray(), nodeKey)
        val newHeaders = headers.toMutableMap().apply {
            this["X-LIGHTSPARK-SIGNING"] =
                "{\"v\":1,\"signature\":\"${signature.base64Encoded}\"}"
        }
        return newBodyData to newHeaders
    }

    private fun anHourFromNowISOString() =
        (Clock.System.now().plus(DateTimePeriod(hours = 1), TimeZone.UTC))
            .toLocalDateTime(TimeZone.UTC)
            .format("yyyy-MM-dd'T'HH:mm:ss'Z'")
}
