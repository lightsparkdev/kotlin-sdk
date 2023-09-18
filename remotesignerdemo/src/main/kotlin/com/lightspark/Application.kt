package com.lightspark

import com.lightspark.plugins.configureHTTP
import com.lightspark.plugins.configureMonitoring
import com.lightspark.plugins.configureRouting
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val config = RemoteSigningConfig.fromEnv()
    configureHTTP()
    configureMonitoring()
    configureRouting(config)
}
