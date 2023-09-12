package com.lightspark

import io.ktor.server.application.ApplicationCall

fun ApplicationCall.debugLog(message: String) {
    application.environment.log.debug(message)
}
