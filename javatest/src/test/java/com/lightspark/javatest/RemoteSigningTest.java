package com.lightspark.javatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lightspark.sdk.LightsparkFuturesClient;
import com.lightspark.sdk.LightsparkSyncClient;
import com.lightspark.sdk.core.requester.JsonObjectBuilderKt;
import com.lightspark.sdk.core.requester.Query;
import com.lightspark.sdk.model.EntityId;
import com.lightspark.sdk.model.SignInvoiceOutput;
import com.lightspark.sdk.model.WebhookEventType;
import com.lightspark.sdk.remotesigning.FuturesRemoteSigning;
import com.lightspark.sdk.remotesigning.RemoteSigning;
import com.lightspark.sdk.webhooks.WebhookEvent;
import com.lightspark.sdk.webhooks.Webhooks;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;

import java.util.concurrent.CompletableFuture;

import kotlin.Unit;
import kotlinx.serialization.json.Json;

public class RemoteSigningTest {

    @Test
    public void testParseEvent() throws Exception {
        String data = "{\"event_type\": \"REMOTE_SIGNING\", \"event_id\": \"8be9c360a68e420b9126b43ff6007a32\", \"timestamp\": \"2023-08-10T02:14:27.559234+00:00\", \"entity_id\": \"node_with_server_signing:0189d6bc-558d-88df-0000-502f04e71816\", \"data\": {\"sub_event_type\": \"GET_PER_COMMITMENT_POINT\", \"bitcoin_network\": \"TESTNET\", \"derivation_path\": \"m/3/2104864975\", \"per_commitment_point_idx\": 281474976710654}}";
        WebhookEvent event = Webhooks.parseWebhook(data.getBytes());
        assertNotNull(event);
        assertEquals(WebhookEventType.REMOTE_SIGNING, event.getEventType());
        assertEquals("8be9c360a68e420b9126b43ff6007a32", event.getEventId());
        JsonObject dataJson = JsonParser.parseString(event.getDataJsonString()).getAsJsonObject();
        assertEquals(dataJson.get("sub_event_type").getAsString(), "GET_PER_COMMITMENT_POINT");
    }

    @Test
    public void testHandleSignInvoiceEventSync() throws Exception {
        LightsparkSyncClient syncClient = mock(LightsparkSyncClient.class);
        ArgumentCaptor<Query<SignInvoiceOutput>> queryCaptor = ArgumentCaptor.forClass(Query.class);
        when(syncClient.executeQuery(any())).thenAnswer((Answer<SignInvoiceOutput>) invocation -> {
            SignInvoiceOutput output = new SignInvoiceOutput(new EntityId("Invoice:123456ab"));
            invocation.getArgument(0, Query.class).component4().invoke(signInvoiceOutputToJson(output));
            return new SignInvoiceOutput(new EntityId("Invoice:123456ab"));
        });

        byte[] privateKeySeed = hexToByteArray("fffcf9f6f3f0edeae7e4e1dedbd8d5d2cfccc9c6c3c0bdbab7b4b1aeaba8a5a29f9c999693908d8a8784817e7b7875726f6c696663605d5a5754514e4b484542");
        String data = "{\"event_type\": \"REMOTE_SIGNING\", \"event_id\": \"8be9c360a68e420b9126b43ff6007a32\", \"timestamp\": \"2023-08-10T02:14:27.559234+00:00\", \"entity_id\": \"node_with_server_signing:0189d6bc-558d-88df-0000-502f04e71816\", \"data\": { \"sub_event_type\": \"SIGN_INVOICE\", \"bitcoin_network\": \"REGTEST\", \"invoice_id\": \"Invoice:123456abcde\", \"payreq_hash\": \"967fe042cfe74f81cb6ce3394c9558748413c8c3c12c98b2c952499fb171a7dd\"}}";
        WebhookEvent event = Webhooks.parseWebhook(data.getBytes());

        assertNotNull(event);
        assertEquals(WebhookEventType.REMOTE_SIGNING, event.getEventType());
        String result = RemoteSigning.handleRemoteSigningEventSync(syncClient, event, privateKeySeed);
        assertNotNull(result);
        System.out.println(result);

        verify(syncClient).executeQuery(queryCaptor.capture());
        Query<SignInvoiceOutput> query = queryCaptor.getValue();
        assertNotNull(query);
    }

    @Test
    public void testHandleSignInvoiceEventFuture() throws Exception {
        LightsparkFuturesClient client = mock(LightsparkFuturesClient.class);
        ArgumentCaptor<Query<SignInvoiceOutput>> queryCaptor = ArgumentCaptor.forClass(Query.class);
        when(client.executeQuery(any())).thenAnswer((Answer<CompletableFuture<SignInvoiceOutput>>) invocation -> {
            SignInvoiceOutput output = new SignInvoiceOutput(new EntityId("Invoice:123456ab"));
            invocation.getArgument(0, Query.class).component4().invoke(signInvoiceOutputToJson(output));
            return CompletableFuture.completedFuture(
                    new SignInvoiceOutput(new EntityId("Invoice:123456ab"))
            );
        });

        byte[] privateKeySeed = hexToByteArray("fffcf9f6f3f0edeae7e4e1dedbd8d5d2cfccc9c6c3c0bdbab7b4b1aeaba8a5a29f9c999693908d8a8784817e7b7875726f6c696663605d5a5754514e4b484542");
        String data = "{\"event_type\": \"REMOTE_SIGNING\", \"event_id\": \"8be9c360a68e420b9126b43ff6007a32\", \"timestamp\": \"2023-08-10T02:14:27.559234+00:00\", \"entity_id\": \"node_with_server_signing:0189d6bc-558d-88df-0000-502f04e71816\", \"data\": { \"sub_event_type\": \"SIGN_INVOICE\", \"bitcoin_network\": \"REGTEST\", \"invoice_id\": \"Invoice:123456abcde\", \"payreq_hash\": \"967fe042cfe74f81cb6ce3394c9558748413c8c3c12c98b2c952499fb171a7dd\"}}";
        WebhookEvent event = Webhooks.parseWebhook(data.getBytes());

        assertNotNull(event);
        assertEquals(WebhookEventType.REMOTE_SIGNING, event.getEventType());
        String result = FuturesRemoteSigning.handleRemoteSigningEvent(client, event, privateKeySeed).get();
        assertNotNull(result);
        System.out.println(result);

        verify(client).executeQuery(queryCaptor.capture());
        Query<SignInvoiceOutput> query = queryCaptor.getValue();
        assertNotNull(query);
    }

    private byte[] hexToByteArray(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string: " + hexString);
        }

        byte[] byteArray = new byte[hexString.length() / 2];

        for (int i = 0; i < byteArray.length; i++) {
            int high = Character.digit(hexString.charAt(i * 2), 16);
            int low = Character.digit(hexString.charAt(i * 2 + 1), 16);

            byteArray[i] = (byte) ((high << 4) | low);
        }

        return byteArray;
    }

    private kotlinx.serialization.json.JsonObject signInvoiceOutputToJson(SignInvoiceOutput output) {
        return JsonObjectBuilderKt.buildJsonObject(Json.Default, jsonObjectBuilder -> {
            kotlinx.serialization.json.JsonObject entityObj = JsonObjectBuilderKt.buildJsonObject(Json.Default, jsonEntity -> {
                jsonEntity.add("id", output.getInvoiceId().getId());
                return Unit.INSTANCE;
            });
            kotlinx.serialization.json.JsonObject outputJsonObj = JsonObjectBuilderKt.buildJsonObject(Json.Default, outputJsonObjBuilder -> {
                outputJsonObjBuilder.add("sign_invoice_output_invoice", entityObj);
                return Unit.INSTANCE;
            });
            jsonObjectBuilder.add("sign_invoice", outputJsonObj);
            return Unit.INSTANCE;
        });
    }
}
