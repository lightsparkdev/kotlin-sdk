package com.lightspark.plugins

import com.lightspark.UmaConfig
import com.lightspark.sdk.uma.InMemoryPublicKeyCache
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(config: UmaConfig) {
    val pubKeyCache = InMemoryPublicKeyCache()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
