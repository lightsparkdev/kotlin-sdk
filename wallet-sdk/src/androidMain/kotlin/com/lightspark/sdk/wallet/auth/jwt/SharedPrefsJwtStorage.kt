package com.lightspark.sdk.wallet.auth.jwt

import android.content.Context
import com.lightspark.sdk.wallet.util.serializerFormat
import java.util.concurrent.atomic.AtomicReference
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class SharedPrefsJwtStorage(context: Context) : JwtStorage {
    private val jwtPrefs = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE)
    private val currentState = AtomicReference<JwtTokenInfo?>()

    override fun getCurrent(): JwtTokenInfo? {
        return currentState.get() ?: run {
            val state = readState()
            if (currentState.compareAndSet(null, state)) {
                state
            } else {
                currentState.get()
            }
        }
    }

    override fun replace(state: JwtTokenInfo?) {
        val prevState = currentState.getAndSet(state)
        if (prevState != state) {
            writeState(state)
        }
    }

    private fun readState(): JwtTokenInfo? {
        val stateJson = jwtPrefs.getString(STATE_JSON_KEY, null)
        return stateJson?.let { serializerFormat.decodeFromString(it) }
    }

    private fun writeState(state: JwtTokenInfo?) {
        val authPrefsEditor = jwtPrefs.edit()
        if (state == null) {
            authPrefsEditor.remove(STATE_JSON_KEY)
        } else {
            authPrefsEditor.putString(STATE_JSON_KEY, serializerFormat.encodeToString(state))
        }
        authPrefsEditor.apply()
    }

    companion object {
        private const val STORE_NAME = "jwtAuth"
        private const val STATE_JSON_KEY = "jwtInfoJson"
    }
}
