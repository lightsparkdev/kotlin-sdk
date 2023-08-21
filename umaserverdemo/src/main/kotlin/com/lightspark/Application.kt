package com.lightspark

import com.lightspark.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val config = UmaConfig.fromEnv()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting(config)
}
