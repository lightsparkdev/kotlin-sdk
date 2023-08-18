package com.lightspark.sdk.webhooks

import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.model.WebhookEventType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlinx.serialization.json.jsonPrimitive

@OptIn(ExperimentalCoroutinesApi::class)
class WebhookTests {
    @Test
    fun `test valid verifyAndParse`() = runTest {
        val eventType = WebhookEventType.NODE_STATUS
        val eventId = "1615c8be5aa44e429eba700db2ed8ca5"
        val entityId = "lightning_node:01882c25-157a-f96b-0000-362d42b64397"
        val timestamp = Instant.parse("2023-05-17T23:56:47.874449+00:00")
        val data = """{"event_type": "NODE_STATUS", "event_id": "1615c8be5aa44e429eba700db2ed8ca5", "timestamp": "2023-05-17T23:56:47.874449+00:00", "entity_id": "lightning_node:01882c25-157a-f96b-0000-362d42b64397"}"""
        val hexdigest = "62a8829aeb48b4142533520b1f7f86cdb1ee7d718bf3ea15bc1c662d4c453b74"
        val webhookSecret = "3gZ5oQQUASYmqQNuEk0KambNMVkOADDItIJjzUlAWjX"

        val event = verifyAndParseWebhook(data.encodeToByteArray(), hexdigest, webhookSecret)
        assertEquals(eventType, event.eventType)
        assertEquals(eventId, event.eventId)
        assertEquals(entityId, event.entityId)
        assertEquals(timestamp, event.timestamp)
        assertNull(event.walletId)
        assertNull(event.data)
    }

    @Test
    fun `test invalid signature`() = runTest {
        val data = """{"event_type": "NODE_STATUS", "event_id": "1615c8be5aa44e429eba700db2ed8ca5", "timestamp": "2023-05-17T23:56:47.874449+00:00", "entity_id": "lightning_node:01882c25-157a-f96b-0000-362d42b64397"}"""
        val hexdigest = "deadbeef"
        val webhookSecret = "3gZ5oQQUASYmqQNuEk0KambNMVkOADDItIJjzUlAWjX"

        try {
            verifyAndParseWebhook(data.encodeToByteArray(), hexdigest, webhookSecret)
            fail("Expected LightsparkException")
        } catch (e: LightsparkException) {
            assertEquals("webhook_signature_verification_failed", e.errorCode)
        }
    }

    @Test
    fun `test invalid json structure`() = runTest {
        val data = """{"event_typeeee": "NODE_STATUS", "event_id": "1615c8be5aa44e429eba700db2ed8ca5", "timestamp": "2023-05-17T23:56:47.874449+00:00", "entity_id": "lightning_node:01882c25-157a-f96b-0000-362d42b64397"}"""
        val hexdigest = "4c4232ea3cccf8d40f56f873ef3a353ad8c80f2e6ea3404197d08c4d46274bf4"
        val webhookSecret = "3gZ5oQQUASYmqQNuEk0KambNMVkOADDItIJjzUlAWjX"

        try {
            verifyAndParseWebhook(data.encodeToByteArray(), hexdigest, webhookSecret)
            fail("Expected Exception")
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("event_type"))
        }
    }

    @Test
    fun `test valid verifyAndParse with wallet`() = runTest {
        val eventType = WebhookEventType.WALLET_INCOMING_PAYMENT_FINISHED
        val eventId = "1615c8be5aa44e429eba700db2ed8ca5"
        val entityId = "lightning_node:01882c25-157a-f96b-0000-362d42b64397"
        val walletId = "wallet:01882c25-157a-f96b-0000-362d42b64397"
        val timestamp = Instant.parse("2023-05-17T23:56:47.874449+00:00")
        val data = """{"event_type": "WALLET_INCOMING_PAYMENT_FINISHED", "event_id": "1615c8be5aa44e429eba700db2ed8ca5", "timestamp": "2023-05-17T23:56:47.874449+00:00", "entity_id": "lightning_node:01882c25-157a-f96b-0000-362d42b64397", "wallet_id": "wallet:01882c25-157a-f96b-0000-362d42b64397" }"""
        val hexdigest = "b4eeb95f18956b3c33b99e9effc61636effc4634f83604cb41de13470c42669a"
        val webhookSecret = "3gZ5oQQUASYmqQNuEk0KambNMVkOADDItIJjzUlAWjX"

        val event = verifyAndParseWebhook(data.encodeToByteArray(), hexdigest, webhookSecret)
        assertEquals(eventType, event.eventType)
        assertEquals(eventId, event.eventId)
        assertEquals(entityId, event.entityId)
        assertEquals(walletId, event.walletId)
        assertEquals(timestamp, event.timestamp)
        assertNull(event.data)
    }

    @Test
    fun `test valid verifyAndParse with data`() = runTest {
        val data = """{"event_type": "REMOTE_SIGNING", "event_id": "8be9c360a68e420b9126b43ff6007a32", "timestamp": "2023-08-10T02:14:27.559234+00:00", "entity_id": "node_with_server_signing:0189d6bc-558d-88df-0000-502f04e71816", "data": {"sub_event_type": "GET_PER_COMMITMENT_POINT", "bitcoin_network": "TESTNET", "derivation_path": "m/3/2104864975", "per_commitment_point_idx": 281474976710654}}"""
        val event = parseWebhook(data.encodeToByteArray())
        assertEquals(WebhookEventType.REMOTE_SIGNING, event.eventType)
        assertNotNull(event.data)
        assertEquals("GET_PER_COMMITMENT_POINT", event.data!!["sub_event_type"]?.jsonPrimitive?.content)
    }
}
