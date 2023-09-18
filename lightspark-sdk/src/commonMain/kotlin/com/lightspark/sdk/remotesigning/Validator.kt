package com.lightspark.sdk.remotesigning

import com.lightspark.sdk.webhooks.WebhookEvent

interface Validator {
    fun shouldSign(event: WebhookEvent): Boolean
}

object AlwaysSignValidator : Validator {
    override fun shouldSign(event: WebhookEvent): Boolean {
        return true
    }
}
