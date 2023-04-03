package com.lightspark.javatest;

import com.lightspark.sdk.LightsparkClient;
import com.lightspark.sdk.graphql.AccountDashboard;
import com.lightspark.sdk.model.BitcoinNetwork;
import com.lightspark.sdk.requester.ServerEnvironment;

import org.junit.jupiter.api.Test;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class LightsparkJavaTest {
    private final LightsparkClient client = new LightsparkClient.Builder()
            .tokenId("0185c15936bf4f89000019ac0f816213")
            .token("pvKTJfP-DFz66U8ofen9Z2my6nt7ImcpS3rCgW6Ohbs")
            .bitcoinNetwork(BitcoinNetwork.REGTEST)
            .serverUrl(ServerEnvironment.DEV.getGraphQLUrl())
            .build();

    @Test
    public void testFullAccountDashboard() {
        Deferred<AccountDashboard> deferred = BuildersKt.async(
                GlobalScope.INSTANCE,
                (CoroutineContext) Dispatchers.getIO(),
                CoroutineStart.DEFAULT, // CoroutineStart.LAZY, or other strategies
                (Function2<CoroutineScope, Continuation<? super AccountDashboard>, AccountDashboard>) (coroutineScope, continuation) -> {
                    AccountDashboard account = (AccountDashboard) client.getFullAccountDashboard(BitcoinNetwork.REGTEST, null, continuation);
                    if (account == null) {
                        throw new NullPointerException("account is null");
                    }
                    return account;
                }
        );
        deferred.invokeOnCompletion((throwable) -> {
            if (throwable != null) {
                throw new RuntimeException(throwable);
            }
            AccountDashboard dashboard = deferred.getCompleted();
            System.out.println(dashboard);
            return null;
        });
    }

}