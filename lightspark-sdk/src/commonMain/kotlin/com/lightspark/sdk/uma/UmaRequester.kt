package com.lightspark.sdk.uma

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

interface UmaRequester {
    suspend fun makeGetRequest(url: String): String
}

class KtorUmaRequester @JvmOverloads constructor(private val client: HttpClient = HttpClient()) : UmaRequester {
    override suspend fun makeGetRequest(url: String): String {
        val response = client.get(url)

        if (response.status.isSuccess()) {
            return response.bodyAsText()
        } else {
            throw Exception("Error making request: ${response.status}")
        }
    }
}
