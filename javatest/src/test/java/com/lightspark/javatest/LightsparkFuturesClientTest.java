package com.lightspark.javatest;

import static com.lightspark.sdk.core.util.PlatformKt.getPlatform;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.lightspark.sdk.ClientConfig;
import com.lightspark.sdk.LightsparkFuturesClient;
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider;
import com.lightspark.sdk.core.requester.Query;
import com.lightspark.sdk.core.requester.ServerEnvironment;
import com.lightspark.sdk.graphql.AccountDashboard;
import com.lightspark.sdk.model.Account;
import com.lightspark.sdk.model.AccountToNodesConnection;
import com.lightspark.sdk.model.AccountToTransactionsConnection;
import com.lightspark.sdk.model.BitcoinNetwork;
import com.lightspark.sdk.model.CurrencyAmount;
import com.lightspark.sdk.model.LightsparkNode;
import com.lightspark.sdk.model.Transaction;
import com.lightspark.sdk.model.WithdrawalMode;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LightsparkFuturesClientTest {
    // Read the token ID and secrets from the environment.
    private static final String API_TOKEN_CLIENT_ID = getPlatform().getEnv("LIGHTSPARK_API_TOKEN_CLIENT_ID");
    private static final String API_TOKEN_CLIENT_SECRET = getPlatform().getEnv("LIGHTSPARK_API_TOKEN_CLIENT_SECRET");
    private final ClientConfig config = new ClientConfig()
            .setAuthProvider(new AccountApiTokenAuthProvider(API_TOKEN_CLIENT_ID, API_TOKEN_CLIENT_SECRET))
            .setDefaultBitcoinNetwork(BitcoinNetwork.REGTEST)
            .setServerUrl(ServerEnvironment.DEV.getGraphQLUrl());

    private final LightsparkFuturesClient client = new LightsparkFuturesClient(config);
    private static final int TIMEOUT_SEC = 5;

    @Test
    public void testAccountDashboard() throws Exception {
        AccountDashboard dashboard = client.getFullAccountDashboard(BitcoinNetwork.REGTEST, null).get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(dashboard);
        System.out.println(dashboard);
    }

    @Test
    public void testNodeQueries() throws Exception {
        Account account = client.getCurrentAccount().get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(account);
        List<LightsparkNode> nodes = client.executeQuery(account.getNodesQuery()).get(TIMEOUT_SEC, TimeUnit.SECONDS).getEntities();
        assertNotNull(nodes);
        assertTrue(nodes.size() > 0);
        System.out.println(nodes.get(0));
    }

    @Test
    public void testRawQuery() throws Exception {
        Map<String, String> variables = new HashMap<>();
        variables.put("network", BitcoinNetwork.REGTEST.name());
        Integer conductivity = client.executeQuery(new Query<>(
                        "query MyCustomFuturesQuery($network: BitcoinNetwork!) {" +
                                "              current_account {" +
                                "                conductivity(bitcoin_networks: [$network])" +
                                "              }" +
                                "            }",
                        variables,
                        jsonString -> {
                            Gson gson = new Gson();
                            MyCustomFuturesQuery result = gson.fromJson(jsonString, MyCustomFuturesQuery.class);
                            return result.current_account.conductivity;

                        }
                )
        ).get(TIMEOUT_SEC, TimeUnit.SECONDS);
        System.out.println("My conductivity is " + conductivity);
    }

    @Test
    public void testFirstPageOfPagination() throws Exception {
        Account account = client.getCurrentAccount().get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(account);
        AccountToTransactionsConnection connection = client.executeQuery(
                account.getTransactionsQuery(20)
        ).get(TIMEOUT_SEC, TimeUnit.SECONDS);

        assertNotNull(connection);
        assertTrue(connection.getEntities().size() > 0);
        System.out.println("The total number of transactions is " + connection.getCount());

        // Let's print the IDs for the current page (the first 20 transactions)
        for (Transaction transaction : connection.getEntities()) {
            System.out.println("Transaction ID = " + transaction.getId());
        }
    }

    @Test
    public void testAllPagesOfPagination() throws Exception {
        Account account = client.getCurrentAccount().get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(account);
        AccountToTransactionsConnection connection = client.executeQuery(
                account.getTransactionsQuery(20)
        ).get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(connection);

        int MAX_ITERATIONS = 10;
        int numIterations = 1;
        while (Boolean.TRUE.equals(connection.getPageInfo().getHasNextPage()) && numIterations < MAX_ITERATIONS) {
            System.out.println("Fetching the following page.");
            numIterations = numIterations + 1;
            connection = client.executeQuery(account.getTransactionsQuery(
                    20,
                    connection.getPageInfo().getEndCursor()
            )).get(TIMEOUT_SEC, TimeUnit.SECONDS);
            assertNotNull(connection);
            for (Transaction transaction : connection.getEntities()) {
                System.out.println("Transaction ID = " + transaction.getId());
            }
        }
        System.out.println("Everything was loaded!");
    }

    @Test
    public void testEntityRequest() throws Exception {
        String nodeId = getNodeId();
        LightsparkNode node = client.executeQuery(LightsparkNode.getLightsparkNodeQuery(nodeId))
                .get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(node);
        System.out.println("Node ID = " + node.getId());
    }

    @Test
    public void testGetWithdrawalFeeEstimate() throws Exception {
        String nodeId = getNodeId();
        CurrencyAmount estimate = client.getWithdrawalFeeEstimate(nodeId, 100, WithdrawalMode.WALLET_ONLY)
                .get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(estimate);
        System.out.println("Withdrawal fee estimate: " + estimate);
    }

    private String getNodeId() throws Exception {
        Account account = client.getCurrentAccount().get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(account);
        AccountToNodesConnection connection = client.executeQuery(
                account.getNodesQuery(1)
        ).get(TIMEOUT_SEC, TimeUnit.SECONDS);
        assertNotNull(connection);
        return connection.getEntities().get(0).getId();
    }
}

class MyCustomFuturesQuery {
    static class CurrentAccount {
        int conductivity;
    }

    CurrentAccount current_account;
}
