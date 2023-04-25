package com.lightspark.sdk.server.auth

import com.lightspark.sdk.core.auth.BETA_HEADER_KEY
import com.lightspark.sdk.core.auth.BETA_HEADER_VALUE
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic

internal class OAuthCustomClientAuthentication(clientSecret: String) : ClientAuthentication {
    private val basicClientDelegate = ClientSecretBasic(clientSecret)

    override fun getRequestHeaders(clientId: String): MutableMap<String, String> =
        basicClientDelegate.getRequestHeaders(clientId).toMutableMap().apply {
            put(BETA_HEADER_KEY, BETA_HEADER_VALUE)
        }

    override fun getRequestParameters(clientId: String): MutableMap<String, String>? =
        basicClientDelegate.getRequestParameters(clientId)
}
