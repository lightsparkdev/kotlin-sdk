package com.lightspark.sdk

import com.lightspark.sdk.model.TransactionStatus
import com.lightspark.sdk.util.serializerFormat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SerializingTests {
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
