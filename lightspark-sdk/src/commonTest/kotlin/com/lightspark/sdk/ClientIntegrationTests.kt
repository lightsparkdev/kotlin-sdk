package com.lightspark.sdk

import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.core.util.getPlatform
import com.lightspark.sdk.crypto.PasswordRecoverySigningKeyLoader
import com.lightspark.sdk.model.Account
import com.lightspark.sdk.model.BitcoinNetwork
import com.lightspark.sdk.model.IncomingPayment
import com.lightspark.sdk.model.LightsparkNode
import com.lightspark.sdk.model.OutgoingPayment
import com.lightspark.sdk.model.Transaction
import com.lightspark.sdk.model.TransactionStatus
import com.lightspark.sdk.model.WithdrawalMode
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class ClientIntegrationTests {
    // Read the token ID and secrets from the environment.
    private val API_TOKEN_CLIENT_ID = getPlatform().getEnv("LIGHTSPARK_API_TOKEN_CLIENT_ID")!!
    private val API_TOKEN_CLIENT_SECRET = getPlatform().getEnv("LIGHTSPARK_API_TOKEN_CLIENT_SECRET")!!
    private val NODE_PASSWORD = getPlatform().getEnv("LIGHTSPARK_TEST_NODE_PASSWORD") ?: "1234!@#$"

    private val config = ClientConfig()
        .setServerUrl(ServerEnvironment.DEV.graphQLUrl)
        .setAuthProvider(
            AccountApiTokenAuthProvider(
                API_TOKEN_CLIENT_ID,
                API_TOKEN_CLIENT_SECRET,
            ),
        )
        .setDefaultBitcoinNetwork(BitcoinNetwork.REGTEST)
    private val client = spy(LightsparkCoroutinesClient(config))

    @Test
    fun `get full account dashboard`() = runTest {
        val dashboard = client.getFullAccountDashboard()
        dashboard.shouldNotBeNull()
        val nodeConnection = dashboard.nodeConnection
        nodeConnection.shouldNotBeNull()
        nodeConnection.nodes.shouldNotBeEmpty()
    }

    @Test
    fun testGetBitcoinFeeEstimates() = runTest {
        val estimates = client.getBitcoinFeeEstimate()
        estimates.shouldNotBeNull()
        estimates.feeFast.shouldNotBeNull()
        estimates.feeMin.shouldNotBeNull()
        println("L1 fee estimate: $estimates")
    }

    @Test
    fun `get withdrawal fee estimate`() = runTest {
        val estimate = client.getWithdrawalFeeEstimate(getNodeId(), amountSats = 1000, WithdrawalMode.WALLET_ONLY)
        estimate.shouldNotBeNull()
        println("Withdrawal fee estimate: $estimate")
    }

    @Test
    fun `list current account nodes`() = runTest {
        val account = getCurrentAccount()
        val nodes = account.getNodesQuery().execute(client)
        nodes.shouldNotBeNull()
        nodes.entities.shouldNotBeEmpty()
        println("Got ${nodes.entities.size} nodes")
        println("Node IDs: ${nodes.entities.map { it.id }}")
    }

    @Test
    fun `create and delete api tokens`() = runTest {
        val account = getCurrentAccount()
        val tokens = account.getApiTokensQuery().execute(client)
        tokens.shouldNotBeNull()

        val token = client.createApiToken("test token", transact = false, testMode = true)
        token.shouldNotBeNull()
        val newTokens = account.getApiTokensQuery().execute(client)
        newTokens.shouldNotBeNull()
        newTokens.entities.shouldHaveSize(tokens.entities.size + 1)

        client.deleteApiToken(token.apiToken.id)
        val finalTokens = account.getApiTokensQuery().execute(client)
        finalTokens.shouldNotBeNull()
        finalTokens.entities.shouldNotBeEmpty()
        finalTokens.entities.shouldHaveSize(tokens.entities.size)
    }

    @Test
    fun `get account conductivity`() = runTest {
        val account = getCurrentAccount()
        val conductivity = account.getConductivityQuery().execute(client)
        conductivity.shouldNotBeNull()
        println("Conductivity: $conductivity")
    }

    @Test
    fun `get balances`() = runTest {
        val account = getCurrentAccount()
        val localBalance = account.getLocalBalanceQuery().execute(client)
        val remoteBalance = account.getRemoteBalanceQuery().execute(client)
        localBalance.shouldNotBeNull()
        remoteBalance.shouldNotBeNull()
        println("Local balance: $localBalance")
        println("Remote balance: $remoteBalance")
    }

    @Test
    fun `get transactions`() = runTest {
        val account = getCurrentAccount()
        val pageSize = 10
        var iterations = 0
        var hasNext = true
        var after: String? = null
        while (hasNext && iterations < 30) {
            iterations++
            val transactions = account.getTransactionsQuery(
                first = pageSize,
                after = after,
            ).execute(client)
            transactions.shouldNotBeNull()
            transactions.entities.shouldNotBeEmpty()
            println("Got ${transactions.entities.size} transactions")
            if (transactions.pageInfo.hasNextPage == true) {
                after = transactions.pageInfo.endCursor
                hasNext = true
                println("  and we have another page!")
            } else {
                hasNext = false
                println("  and we're done!")
            }
        }
    }

    @Test
    fun `get transactions in the last day`() = runTest {
        val account = getCurrentAccount()
        val transactions = account.getTransactionsQuery(
            afterDate = Clock.System.now().minus(DateTimePeriod(days = 1), TimeZone.UTC),
        ).execute(client)
        transactions.shouldNotBeNull()
        println("Got ${transactions.entities.size} transactions in the last day")
    }

    @Test
    fun `get most recent transaction details`() = runTest {
        val account = getCurrentAccount()
        val transactions = account.getTransactionsQuery(
            first = 1,
        ).execute(client)
        transactions.shouldNotBeNull()
        transactions.entities.shouldNotBeEmpty()
        val transaction = transactions.entities.first()
        val details = Transaction.getTransactionQuery(transaction.id).execute(client)
        details.shouldNotBeNull()
        println("Transaction details: $details")
    }

    @Test
    fun `create and decode a payment request`() = runTest {
        val paymentRequest = client.createInvoice(
            getNodeId(),
            42000,
            "Pizza!",
        )

        println("encoded invoice: ${paymentRequest.data.encodedPaymentRequest}")

        val decoded = client.decodeInvoice(paymentRequest.data.encodedPaymentRequest)
        decoded.shouldNotBeNull()
        println("decoded invoice: $decoded")
    }

    @Test
    fun `create an LNURL invoice`() = runTest {
        val node = getFirstOskNode()
        val metadata = "[[\\\"text/plain\\\",\\\"Pay to domain.org user ktfan98\\\"]," +
            "[\\\"text/identifier\\\",\\\"ktfan98@domain.org\\\"]]"
        val paymentRequest = client.createLnurlInvoice(node.id, 1000, metadata)

        println("encoded invoice: ${paymentRequest.data.encodedPaymentRequest}")
    }

    @Test
    fun `create and pay an UMA invoice`() = runTest {
        val node = getFirstOskNode()
        val metadata = "[[\\\"text/plain\\\",\\\"Pay to domain.org user ktfan98\\\"]," +
            "[\\\"text/identifier\\\",\\\"ktfan98@domain.org\\\"]]"
        val paymentRequest = client.createUmaInvoice(
            node.id,
            1000,
            metadata,
            signingPrivateKey = Random.nextBytes(5),
            receiverIdentifier = "ktfan98@domain.org",
        )
        println("encoded invoice: ${paymentRequest.data.encodedPaymentRequest}")

        client.loadNodeSigningKey(node.id, PasswordRecoverySigningKeyLoader(node.id, NODE_PASSWORD))
        val payment = client.payUmaInvoice(
            node.id,
            paymentRequest.data.encodedPaymentRequest,
            60,
            signingPrivateKey = Random.nextBytes(5),
            senderIdentifier = "sender@domain.org",
        )
        payment.shouldNotBeNull()
        println("Payment: $payment")
    }

    @Test
    fun `create and cancel an invoice`() = runTest {
        val node = getFirstOskNode()
        val invoice = client.createInvoice(node.id, 1000)

        println("encoded invoice: $invoice.data.encodedPaymentRequest}")

        val cancelledInvoice = client.cancelInvoice(invoice.id)
        cancelledInvoice.shouldNotBeNull()
        println("cancelled invoice: $cancelledInvoice")
    }

    @Test
    fun `send a payment for an invoice`() = runTest {
        val node = getFirstOskNode()
        client.loadNodeSigningKey(node.id, PasswordRecoverySigningKeyLoader(node.id, NODE_PASSWORD))

        // Just paying a pre-existing AMP invoice.
        val ampInvoice =
            "lnbcrt1pjr8xwypp5xqj2jfpkz095s8zu57ktsq8vt8yazwcmqpcke9pvl67ne9cpdr0qdqj2a5xzumnwd6hqurswqcqzpgxq9z0rgqs" +
                "p55hfn0caa5sexea8u979cckkmwelw6h3zpwel5l8tn8s0elgwajss9q8pqqqssqefmmw79tknhl5xhnh7yfepzypxknwr9r4ya7" +
                "ueqa6vz20axvys8se986hwj6gppeyzst44hm4yl04c4dqjjpqgtt0df254q087sjtfsq35yagj"
        val payment = client.payInvoice(node.id, ampInvoice, maxFeesMsats = 100_000, amountMsats = 10_000)
        payment.shouldNotBeNull()
        println("Payment: $payment")
    }

    @Test
    fun `get node channels`() = runTest {
        val node = getFirstOskNode()
        val channels = node.getChannelsQuery().execute(client)
        channels.shouldNotBeNull()
        channels.entities.shouldNotBeEmpty()
        println("Got ${channels.entities.size} channels")
        println("Channel IDs: ${channels.entities.map { it.id }}")
    }

    @Test
    fun `test paying a test mode invoice`() = runTest {
        val node = getFirstOskNode()
        client.loadNodeSigningKey(node.id, PasswordRecoverySigningKeyLoader(node.id, NODE_PASSWORD))
        val invoice = client.createTestModeInvoice(node.id, 100_000, "test invoice")
        var outgoingPayment: OutgoingPayment? = client.payInvoice(node.id, invoice, maxFeesMsats = 100_000)
        outgoingPayment.shouldNotBeNull()
        while (outgoingPayment?.status == TransactionStatus.PENDING) {
            delay(500)
            outgoingPayment = OutgoingPayment.getOutgoingPaymentQuery(outgoingPayment.id).execute(client)
            println("Payment status: ${outgoingPayment?.status}")
        }
        outgoingPayment?.status.shouldBe(TransactionStatus.SUCCESS)
    }

    @Test
    fun `test creating a test mode payment`() = runTest {
        val node = getFirstOskNode()
        client.loadNodeSigningKey(node.id, PasswordRecoverySigningKeyLoader(node.id, NODE_PASSWORD))
        val invoice = client.createInvoice(node.id, 100_000, "test invoice")
        var payment: IncomingPayment? = client.createTestModePayment(node.id, invoice.data.encodedPaymentRequest)
        payment.shouldNotBeNull()
        while (payment?.status == TransactionStatus.PENDING) {
            delay(500)
            payment = IncomingPayment.getIncomingPaymentQuery(payment.id).execute(client)
            println("Payment status: ${payment?.status}")
        }
        payment?.status.shouldBe(TransactionStatus.SUCCESS)
    }

    @Test
    fun `test getIncomingPaymentsForPaymentHash`() = runTest {
        val node = getFirstOskNode()
        client.loadNodeSigningKey(node.id, PasswordRecoverySigningKeyLoader(node.id, NODE_PASSWORD))
        val invoice = client.createInvoice(node.id, 100_000, "test invoice")
        var payment: IncomingPayment? = client.createTestModePayment(node.id, invoice.data.encodedPaymentRequest)
        payment.shouldNotBeNull()
        while (payment?.status == TransactionStatus.PENDING) {
            delay(500)
            payment = IncomingPayment.getIncomingPaymentQuery(payment.id).execute(client)
            println("Payment status: ${payment?.status}")
        }

        val payments = client.getIncomingPaymentsForPaymentHash(invoice.data.paymentHash)
        payments.shouldNotBeNull()
        payments.shouldHaveSize(1)
        payments[0].id.shouldBe(payment?.id)
    }

    @Test
    fun `test getOutgoingPaymentsForPaymentHash`() = runTest {
        val node = getFirstOskNode()
        client.loadNodeSigningKey(node.id, PasswordRecoverySigningKeyLoader(node.id, NODE_PASSWORD))
        val invoice = client.createTestModeInvoice(node.id, 100_000, "test invoice")
        var outgoingPayment: OutgoingPayment? = client.payInvoice(node.id, invoice, maxFeesMsats = 100_000)
        outgoingPayment.shouldNotBeNull()
        while (outgoingPayment?.status == TransactionStatus.PENDING) {
            delay(500)
            outgoingPayment = OutgoingPayment.getOutgoingPaymentQuery(outgoingPayment.id).execute(client)
            println("Payment status: ${outgoingPayment?.status}")
        }
        outgoingPayment?.status.shouldBe(TransactionStatus.SUCCESS)
        val payments = client.getOutgoingPaymentForPaymentHash(
            client.decodeInvoice(invoice).paymentHash
        )
        payments.shouldNotBeNull()
        payments.shouldHaveSize(1)
        payments[0].id.shouldBe(outgoingPayment?.id!!)
    }

    @Test
    fun `test getInvoiceForPaymentHash`() = runTest {
        val node = getFirstOskNode()
        client.loadNodeSigningKey(node.id, PasswordRecoverySigningKeyLoader(node.id, NODE_PASSWORD))
        val testInvoice = client.createInvoice(node.id, 100_000, "test invoice")
        var payment: IncomingPayment? = client.createTestModePayment(node.id, testInvoice.data.encodedPaymentRequest)
        payment.shouldNotBeNull()
        while (payment?.status == TransactionStatus.PENDING) {
            delay(500)
            payment = IncomingPayment.getIncomingPaymentQuery(payment.id).execute(client)
            println("Payment status: ${payment?.status}")
        }

        val invoice = client.getInvoiceForPaymentHash(testInvoice.data.paymentHash)
        invoice.shouldNotBeNull()
        invoice.id.shouldBe(testInvoice.id)
    }

    @Test
    fun `test uma identifier hashing`() = runTest {
        val privKeyBytes = "xyz".toByteArray()
        `when`(client.getUtcDateTime()).thenReturn(LocalDateTime(2021, 1, 1, 0, 0, 0))
        val hashedUma = client.hashUmaIdentifier("user@domain.com", privKeyBytes)
        val hashedUmaSameMonth = client.hashUmaIdentifier("user@domain.com", privKeyBytes)
        hashedUmaSameMonth.shouldBe(hashedUma)
        println(hashedUma)

        `when`(client.getUtcDateTime()).thenReturn(LocalDateTime(2021, 2, 1, 0, 0, 0))
        val hashedUmaNewMonth = client.hashUmaIdentifier("user@domain.com", privKeyBytes)
        hashedUmaNewMonth.shouldNotBe(hashedUma)
        println(hashedUmaNewMonth)
    }

    // TODO: Add tests for withdrawals and deposits.

    private suspend fun getFirstOskNode(): LightsparkNode {
        val account = getCurrentAccount()
        val nodes = account.getNodesQuery().execute(client)
        nodes.shouldNotBeNull()
        nodes.entities.shouldNotBeEmpty()
        return nodes.entities.first { it.id.contains("OSK") }
    }

    private suspend fun getNodeId(): String {
        return getFirstOskNode().id
    }

    private suspend fun getCurrentAccount(): Account {
        val account = client.getCurrentAccount()
        account.shouldNotBeNull()
        return account
    }
}
