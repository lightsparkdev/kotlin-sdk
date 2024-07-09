package com.lightspark.javatest;

import static com.lightspark.sdk.core.util.PlatformKt.getPlatform;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.lightspark.sdk.ClientConfig;
import com.lightspark.sdk.LightsparkSyncClient;
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider;
import com.lightspark.sdk.core.requester.Query;
import com.lightspark.sdk.core.requester.ServerEnvironment;
import com.lightspark.sdk.crypto.PasswordRecoverySigningKeyLoader;
import com.lightspark.sdk.graphql.AccountDashboard;
import com.lightspark.sdk.model.Account;
import com.lightspark.sdk.model.AccountToNodesConnection;
import com.lightspark.sdk.model.AccountToTransactionsConnection;
import com.lightspark.sdk.model.ApiToken;
import com.lightspark.sdk.model.BitcoinNetwork;
import com.lightspark.sdk.model.CreateApiTokenOutput;
import com.lightspark.sdk.model.CurrencyAmount;
import com.lightspark.sdk.model.FeeEstimate;
import com.lightspark.sdk.model.IncomingPayment;
import com.lightspark.sdk.model.Invoice;
import com.lightspark.sdk.model.LightsparkNode;
import com.lightspark.sdk.model.OutgoingPayment;
import com.lightspark.sdk.model.Transaction;
import com.lightspark.sdk.model.TransactionStatus;
import com.lightspark.sdk.model.WithdrawalMode;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LightsparkSyncClientTest {
    private static final String API_TOKEN_CLIENT_ID = getPlatform().getEnv("LIGHTSPARK_API_TOKEN_CLIENT_ID");
    private static final String API_TOKEN_CLIENT_SECRET = getPlatform().getEnv("LIGHTSPARK_API_TOKEN_CLIENT_SECRET");
    private static final String NODE_PASSWORD = getPlatform().getEnv("LIGHTSPARK_TEST_NODE_PASSWORD");
    private final ClientConfig config = new ClientConfig()
            .setAuthProvider(new AccountApiTokenAuthProvider(API_TOKEN_CLIENT_ID, API_TOKEN_CLIENT_SECRET))
            .setDefaultBitcoinNetwork(BitcoinNetwork.REGTEST)
            .setServerUrl(ServerEnvironment.DEV.getGraphQLUrl());

    private final LightsparkSyncClient client = new LightsparkSyncClient(config);

    @Test
    public void testAccountDashboard() throws Exception {
        AccountDashboard dashboard = client.getFullAccountDashboard(BitcoinNetwork.REGTEST, null);
        assertNotNull(dashboard);
        assertNotNull(dashboard.getNodeConnection());
        assertNotNull(dashboard.getNodeConnection().getNodes());
        System.out.println(dashboard);
    }

    @Test
    public void testNodeQueries() throws Exception {
        Account account = client.getCurrentAccount();
        assertNotNull(account);
        List<LightsparkNode> nodes = Objects.requireNonNull(client.executeQuery(account.getNodesQuery())).getEntities();
        assertNotNull(nodes);
        assertTrue(nodes.size() > 0);
        System.out.println(nodes.get(0));
    }

    @Test
    public void testCreateAndDeleteApiTokens() throws Exception {
        Account account = client.getCurrentAccount();
        List<ApiToken> tokens = Objects.requireNonNull(client.executeQuery(account.getApiTokensQuery())).getEntities();
        assertNotNull(tokens);

        CreateApiTokenOutput token = client.createApiToken("test token", false, true);
        assertNotNull(token);
        List<ApiToken> newTokens = Objects.requireNonNull(client.executeQuery(account.getApiTokensQuery())).getEntities();
        assertNotNull(newTokens);
        assertEquals(tokens.size() + 1, newTokens.size());

        client.deleteApiToken(token.getApiToken().getId());
        List<ApiToken> finalTokens = Objects.requireNonNull(client.executeQuery(account.getApiTokensQuery())).getEntities();
        assertNotNull(finalTokens);
        assertTrue(finalTokens.size() > 0);
        assertEquals(finalTokens.size(), tokens.size());
    }

    @Test
    public void testGetAccountConductivity() throws Exception {
        Account account = client.getCurrentAccount();
        Integer conductivity = client.executeQuery(account.getConductivityQuery());
        assertNotNull(conductivity);
        System.out.println("Conductivity: " + conductivity);
    }

    @Test
    public void testGetBalances() throws Exception {
        Account account = client.getCurrentAccount();
        CurrencyAmount localBalance = client.executeQuery(account.getLocalBalanceQuery());
        CurrencyAmount remoteBalance = client.executeQuery(account.getRemoteBalanceQuery());
        assertNotNull(localBalance);
        assertNotNull(remoteBalance);
        System.out.println("Local balance: " + localBalance);
        System.out.println("Remote balance: " + remoteBalance);
    }

    @Test
    public void testGetMostRecentTransactionDetails() throws Exception {
        Account account = client.getCurrentAccount();
        AccountToTransactionsConnection transactions = client.executeQuery(account.getTransactionsQuery(1));
        assertNotNull(transactions);
        assertTrue(transactions.getEntities().size() > 0);
        Transaction transaction = transactions.getEntities().get(0);
        Transaction details = client.executeQuery(Transaction.getTransactionQuery(transaction.getId()));
        assertNotNull(details);
        System.out.println("Transactions details: " + details);
    }

    @Test
    public void testRawQuery() throws Exception {
        Map<String, String> variables = new HashMap<>();
        variables.put("network", BitcoinNetwork.REGTEST.name());
        Integer conductivity = client.executeQuery(new Query<>(
                        "query MyCustomQuery($network: BitcoinNetwork!) {" +
                                "              current_account {" +
                                "                conductivity(bitcoin_networks: [$network])" +
                                "              }" +
                                "            }",
                        variables,
                        jsonString -> {
                            Gson gson = new Gson();
                            MyCustomQuery result = gson.fromJson(jsonString, MyCustomQuery.class);
                            return result.current_account.conductivity;

                        }
                )
        );
        System.out.println("My conductivity is " + conductivity);
    }

    @Test
    public void testFirstPageOfPagination() throws Exception {
        Account account = client.getCurrentAccount();
        assertNotNull(account);
        AccountToTransactionsConnection connection = client.executeQuery(
                account.getTransactionsQuery(20)
        );

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
        Account account = client.getCurrentAccount();
        assertNotNull(account);
        AccountToTransactionsConnection connection = client.executeQuery(
                account.getTransactionsQuery(20)
        );
        assertNotNull(connection);

        int MAX_ITERATIONS = 10;
        int numIterations = 1;
        while (Boolean.TRUE.equals(connection.getPageInfo().getHasNextPage()) && numIterations < MAX_ITERATIONS) {
            System.out.println("Fetching the following page.");
            numIterations = numIterations + 1;
            connection = client.executeQuery(account.getTransactionsQuery(
                    20,
                    connection.getPageInfo().getEndCursor()
            ));
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
        LightsparkNode node = client.executeQuery(LightsparkNode.getLightsparkNodeQuery(nodeId));
        assertNotNull(node);
        System.out.println("Node ID = " + node.getId());
    }

    @Test
    public void testGetWithdrawalFeeEstimate() throws Exception {
        String nodeId = getNodeId();
        CurrencyAmount estimate = client.getWithdrawalFeeEstimate(nodeId, 1000, WithdrawalMode.WALLET_ONLY);
        assertNotNull(estimate);
        System.out.println("Withdrawal fee estimate: " + estimate);
    }

    @Test
    public void testGetBitcoinFeeEstimates() throws Exception {
        FeeEstimate estimate = client.getBitcoinFeeEstimate();
        assertNotNull(estimate);
        assertNotNull(estimate.getFeeFast());
        assertNotNull(estimate.getFeeMin());
        System.out.println("Bitcoin fee estimate: " + estimate);
    }

    @Test
    public void testCreateLnurlInvoice() throws Exception {
        LightsparkNode node = getFirstNode();
        String metadata = "[[\\\"text/plain\\\",\\\"Pay to domain.org user ktfan98\\\"],[\\\"text/identifier\\\",\\\"ktfan98@domain.org\\\"]]";
        Invoice paymentRequest = client.createLnurlInvoice(node.getId(), 1000, metadata);

        System.out.println("encoded invoice: " + paymentRequest.getData().getEncodedPaymentRequest());
    }

    @Test
    public void testCreateAndCancelInvoice() throws Exception{
        LightsparkNode node = getFirstNode();
        Invoice invoice = client.createInvoice(node.getId(), 1000);
        System.out.println("encoded invoice: " + invoice.getData().getEncodedPaymentRequest());

        Invoice cancelledInvoice = client.cancelInvoice(invoice.getId());
        assertNotNull(cancelledInvoice);
        System.out.println("cancelled invoice: " + cancelledInvoice);
    }

    @Test
    public void testPayingTestModeInvoice() throws Exception {
        LightsparkNode node = getFirstNode();
        client.loadNodeSigningKey(node.getId(), new PasswordRecoverySigningKeyLoader(node.getId(), NODE_PASSWORD));
        String invoice = client.createTestModeInvoice(node.getId(), 100_000, "test invoice");
        OutgoingPayment outgoingPayment = client.payInvoice(node.getId(), invoice, 100_000);
        assertNotNull(outgoingPayment);
        while (outgoingPayment.getStatus() == TransactionStatus.PENDING) {
            Thread.sleep(500);
            outgoingPayment = client.executeQuery(OutgoingPayment.getOutgoingPaymentQuery(outgoingPayment.getId()));
            System.out.println("Payment status: " + outgoingPayment.getStatus());
        }
        assertEquals(outgoingPayment.getStatus(), TransactionStatus.SUCCESS);
    }

    @Test
    public void testCreatingTestModePayment() throws Exception {
        LightsparkNode node = getFirstNode();
        client.loadNodeSigningKey(node.getId(), new PasswordRecoverySigningKeyLoader(node.getId(), NODE_PASSWORD));
        Invoice invoice = client.createInvoice(node.getId(), 100_000, "test invoice");
        IncomingPayment incomingPayment = client.createTestModePayment(node.getId(), invoice.getData().getEncodedPaymentRequest());
        assertNotNull(incomingPayment);
        while (incomingPayment.getStatus() == TransactionStatus.PENDING) {
            Thread.sleep(500);
            incomingPayment = client.executeQuery(IncomingPayment.getIncomingPaymentQuery(incomingPayment.getId()));
            System.out.println("Payment status: " + incomingPayment.getStatus());
        }
        assertEquals(incomingPayment.getStatus(), TransactionStatus.SUCCESS);
    }

    private LightsparkNode getFirstNode() throws Exception {
        Account account = client.getCurrentAccount();
        assertNotNull(account);
        AccountToNodesConnection connection = client.executeQuery(account.getNodesQuery(1));
        assertNotNull(connection);
        return connection.getEntities().get(0);
    }

    private String getNodeId() throws Exception {
        return getFirstNode().getId();
    }
}

class MyCustomQuery {
    static class CurrentAccount {
        int conductivity;
    }

    CurrentAccount current_account;
}
