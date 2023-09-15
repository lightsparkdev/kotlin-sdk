package com.lightspark.javatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.Gson;
import com.lightspark.sdk.model.BitcoinNetwork;
import com.lightspark.sdk.model.CurrencyAmount;
import com.lightspark.sdk.model.CurrencyUnit;
import com.lightspark.sdk.model.GraphNode;
import com.lightspark.sdk.model.Invoice;
import com.lightspark.sdk.model.InvoiceData;
import com.lightspark.sdk.model.PaymentRequestStatus;
import com.lightspark.sdk.uma.InMemoryPublicKeyCache;
import com.lightspark.sdk.uma.PayReqResponse;
import com.lightspark.sdk.uma.PayRequest;
import com.lightspark.sdk.uma.PayerData;
import com.lightspark.sdk.uma.PubKeyResponse;
import com.lightspark.sdk.uma.UmaInvoiceCreator;
import com.lightspark.sdk.uma.UmaProtocolHelper;
import com.lightspark.sdk.uma.UmaRequester;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import kotlin.coroutines.Continuation;
import kotlinx.datetime.Clock;

public class UmaTest {
    UmaProtocolHelper umaProtocolHelper = new UmaProtocolHelper(new InMemoryPublicKeyCache(), new TestUmaRequester());

    @Test
    public void testFetchPublicKeySync() throws Exception {
        PubKeyResponse pubKeys = umaProtocolHelper.fetchPublicKeysForVaspSync("https://vasp.com");
        assertNotNull(pubKeys);
        System.out.println(pubKeys);
    }

    @Test
    public void testFetchPublicKeyFuture() throws Exception {
        PubKeyResponse pubKeys = umaProtocolHelper.fetchPublicKeysForVaspFuture("https://vasp.com").get();
        assertNotNull(pubKeys);
        System.out.println(pubKeys);
    }

    @Test
    public void testGetPayReqResponseSync() throws Exception {
        PayRequest request = new PayRequest(
                "USD",
                100,
                new PayerData("$alice@vasp1.com")
        );
        PayReqResponse response = umaProtocolHelper.getPayReqResponseSync(
                request,
                new TestUmaInvoiceCreator(),
                "metadata",
                "USD",
                12345L,
                List.of(),
                null,
                ""
        );
        assertNotNull(response);
        assertEquals("lnbc12345", response.getEncodedInvoice());
        System.out.println(response);
    }

    @Test
    public void testGetPayReqResponseFuture() throws Exception {
        PayRequest request = new PayRequest(
                "USD",
                100,
                new PayerData("$alice@vasp1.com")
        );
        PayReqResponse response = umaProtocolHelper.getPayReqResponseFuture(
                request,
                new TestUmaInvoiceCreator(),
                "metadata",
                "USD",
                12345L,
                List.of(),
                null,
                ""
        ).get();
        assertNotNull(response);
        assertEquals("lnbc12345", response.getEncodedInvoice());
        System.out.println(response);
    }
}

class TestUmaRequester implements UmaRequester {
    @Nullable
    @Override
    public Object makeGetRequest(@NotNull String url, @NotNull Continuation<? super String> $completion) {
        if (url.contains("lnurlpubkey")) {
            PubKeyResponse response = new PubKeyResponse(
                    "02d5".getBytes(StandardCharsets.UTF_8),
                    "12345".getBytes(StandardCharsets.UTF_8));
            Gson gson = new Gson();
            return gson.toJson(response);
        }
        return null;
    }
}

class TestUmaInvoiceCreator implements UmaInvoiceCreator {
    @NotNull
    @Override
    public CompletableFuture<Invoice> createUmaInvoice(long amountMsats, @NotNull String metadata) {
        return CompletableFuture.completedFuture(new Invoice(
                "lnbc12345",
                Clock.System.INSTANCE.now(),
                Clock.System.INSTANCE.now(),
                new InvoiceData(
                        "lnbc12345",
                        BitcoinNetwork.MAINNET,
                        "12345",
                        new CurrencyAmount(12345, CurrencyUnit.SATOSHI, CurrencyUnit.SATOSHI, 12345, 12345),
                        Clock.System.INSTANCE.now(),
                        Clock.System.INSTANCE.now(),
                        new GraphNode(
                                "1234",
                                Clock.System.INSTANCE.now(),
                                Clock.System.INSTANCE.now(),
                                BitcoinNetwork.MAINNET,
                                "1234",
                                "1234",
                                "blue",
                                100,
                                "38hd"
                        ),
                        null
                ),
                PaymentRequestStatus.CLOSED,
                null
        ));
    }
}