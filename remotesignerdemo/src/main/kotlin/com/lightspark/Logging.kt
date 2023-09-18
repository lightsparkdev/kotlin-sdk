package com.lightspark

import io.ktor.server.application.ApplicationCall

fun ApplicationCall.debugLog(message: String) {
    application.environment.log.debug(message)
}

fun ApplicationCall.errorLog(message: String, exception: Throwable? = null) {
    application.environment.log.error(message, exception)
}
