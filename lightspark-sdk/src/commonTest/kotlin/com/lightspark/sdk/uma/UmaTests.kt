package com.lightspark.sdk.uma

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json

@OptIn(ExperimentalCoroutinesApi::class)
class UmaTests {
    @Test
    fun `test serialize PayerDataOptions`() = runTest {
        val payerDataOptions = PayerDataOptions(
            nameRequired = false,
            emailRequired = false,
            complianceRequired = true,
        )
        val json = payerDataOptions.toJson()
        assertEquals(
            payerDataOptions,
            Json.decodeFromString(PayerDataOptions.serializer(), json),
        )
    }
}
