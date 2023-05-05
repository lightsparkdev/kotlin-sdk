package com.lightspark.sdk.core

import com.lightspark.sdk.core.util.EnumSerializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalCoroutinesApi::class)
class SerializingTests {
    private val serializerFormat = Json { ignoreUnknownKeys = true }

    @Serializable
    data class TestClass(val transactionStatus: TransactionStatus)

    @Serializable
    data class ListTestClass(val transactionStatuses: List<TransactionStatus>)

    @Test
    fun `test basic enum serializing`() = runTest {
        val testClass = TestClass(TransactionStatus.PENDING)
        val serialized = serializerFormat.encodeToString(testClass)
        val deserialized = serializerFormat.decodeFromString<TestClass>(serialized)
        assertEquals(testClass, deserialized)
    }

    @Test
    fun `test future value deserializing`() = runTest {
        val serialized = "{\"transactionStatus\": \"ON_FIRE\"}"
        val deserialized = serializerFormat.decodeFromString<TestClass>(serialized)
        assertEquals(TestClass(TransactionStatus.FUTURE_VALUE), deserialized)
    }

    @Test
    fun `test list of enums`() = runTest {
        val serialized = "[\"ON_FIRE\", \"PENDING\", \"SUCCESS\"]"
        val deserialized = serializerFormat.decodeFromString<List<TransactionStatus>>(serialized)
        assertEquals(
            listOf(TransactionStatus.FUTURE_VALUE, TransactionStatus.PENDING, TransactionStatus.SUCCESS),
            deserialized,
        )
    }

    @Test
    fun `test object with list of enums`() = runTest {
        val testObject = ListTestClass(listOf(TransactionStatus.PENDING, TransactionStatus.SUCCESS))
        val serialized = serializerFormat.encodeToString(testObject)
        assertEquals("{\"transactionStatuses\":[\"PENDING\",\"SUCCESS\"]}", serialized)
        val deserialized = serializerFormat.decodeFromString<ListTestClass>(serialized)
        assertEquals(testObject, deserialized)
    }

    @Test
    fun `test object with list of enums and future value`() = runTest {
        val serialized = "{\"transactionStatuses\":[\"PENDING\",\"SUCCESS\",\"ON_FIRE\"]}"
        val deserialized = serializerFormat.decodeFromString<ListTestClass>(serialized)
        assertEquals(
            ListTestClass(
                listOf(
                    TransactionStatus.PENDING,
                    TransactionStatus.SUCCESS,
                    TransactionStatus.FUTURE_VALUE,
                ),
            ),
            deserialized,
        )
    }
}

@Serializable(with = TransactionStatusSerializer::class)
enum class TransactionStatus(val rawValue: String) {
    /** Transaction succeeded.. **/
    SUCCESS("SUCCESS"),

    /** Transaction failed. **/
    FAILED("FAILED"),

    /** Transaction has been initiated and is currently in-flight. **/
    PENDING("PENDING"),

    /** For transaction type PAYMENT_REQUEST only. No payments have been made to a payment request. **/
    NOT_STARTED("NOT_STARTED"),

    /** For transaction type PAYMENT_REQUEST only. A payment request has expired. **/
    EXPIRED("EXPIRED"),

    /** For transaction type PAYMENT_REQUEST only. **/
    CANCELLED("CANCELLED"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object TransactionStatusSerializer :
    EnumSerializer<TransactionStatus>(
        TransactionStatus::class,
        { rawValue ->
            TransactionStatus.values().firstOrNull { it.rawValue == rawValue } ?: TransactionStatus.FUTURE_VALUE
        },
    )
