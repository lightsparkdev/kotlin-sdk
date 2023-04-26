package com.lightspark.sdk.wallet

import com.lightspark.sdk.core.crypto.generateSigningKeyPair
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.core.util.getPlatform
import com.lightspark.sdk.wallet.auth.jwt.CustomJwtAuthProvider
import com.lightspark.sdk.wallet.auth.jwt.InMemoryJwtStorage
import com.lightspark.sdk.wallet.model.KeyType
import com.lightspark.sdk.wallet.model.Transaction
import com.lightspark.sdk.wallet.model.Wallet
import com.lightspark.sdk.wallet.model.WalletStatus
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldNotBe
import io.ktor.util.decodeBase64Bytes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import saschpe.kase64.base64Encoded
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ClientIntegrationTests {
    // Read the token ID and secrets from the environment.
    private val API_ACCOUNT_ID = getPlatform().getEnv("LIGHTSPARK_ACCOUNT_ID")!!
    private val API_JWT = getPlatform().getEnv("LIGHTSPARK_JWT")!!
    private val WALLET_SIGNING_PUB_KEY = getPlatform().getEnv("LIGHTSPARK_WALLET_PUB_KEY")
    private val WALLET_SIGNING_PRIV_KEY = getPlatform().getEnv("LIGHTSPARK_WALLET_PRIV_KEY")

    private val jwtStorage = InMemoryJwtStorage()
    private val config = ClientConfig()
        .setServerUrl(ServerEnvironment.DEV.graphQLUrl)
        .setAuthProvider(CustomJwtAuthProvider(jwtStorage))
    private val client = LightsparkCoroutinesWalletClient(config)

    private fun runAuthedTest(test: suspend TestScope.() -> Unit) = runTest {
        val output = client.loginWithJWT(API_ACCOUNT_ID, API_JWT, jwtStorage)
        deployWalletIfNotDeployed(output.wallet)
        test()
    }

    private suspend fun deployWalletIfNotDeployed(wallet: Wallet) {
        if (wallet.status != WalletStatus.NOT_SETUP) {
            return
        }
        client.deployWalletAndAwaitDeployed().collect {
            print("Wallet update: $it")
            it.status.shouldNotBe(WalletStatus.FAILED)
            if (it.status == WalletStatus.DEPLOYED) {
                println("Wallet deployed!")
            }
        }
        val keypair = generateSigningKeyPair()
        println(keypair.public.encoded)
        client.loadWalletSigningKey(keypair.private.encoded)
        client.initializeWalletAndWaitForInitialized(KeyType.RSA_OAEP, keypair.public.encoded.base64Encoded).collect {
            print("Wallet update: $it")
            it.status.shouldNotBe(WalletStatus.FAILED)
            if (it.status == WalletStatus.READY) {
                println("Wallet initialized!")
            }
        }
    }

    @Test
    fun `get wallet dashboard`() = runAuthedTest {
        val dashboard = client.getWalletDashboard()
        dashboard.shouldNotBeNull()
        val balances = dashboard.balances
        balances.shouldNotBeNull()
    }

    @Test
    fun testGetBitcoinFeeEstimates() = runAuthedTest {
        val estimates = client.getBitcoinFeeEstimate()
        estimates.shouldNotBeNull()
        estimates.feeFast.shouldNotBeNull()
        estimates.feeMin.shouldNotBeNull()
        println("L1 fee estimate: $estimates")
    }

    @Test
    fun `list current wallet payment requests`() = runAuthedTest {
        val wallet = getCurrentWallet()
        val paymentRequestsConnection = wallet.getPaymentRequestsQuery().execute(client)
        paymentRequestsConnection.shouldNotBeNull()
        paymentRequestsConnection.entities.shouldNotBeEmpty()
        println("Got ${paymentRequestsConnection.entities.size} payment requests")
        println("Request IDs: ${paymentRequestsConnection.entities.map { it.id }}")
    }

    @Test
    fun `get transactions`() = runAuthedTest {
        val wallet = getCurrentWallet()
        val pageSize = 10
        var iterations = 0
        var hasNext = true
        var after: String? = null
        while (hasNext && iterations < 30) {
            iterations++
            val transactions = wallet.getTransactionsQuery(
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
    fun `get transactions in the last day`() = runAuthedTest {
        val wallet = getCurrentWallet()
        val transactions = wallet.getTransactionsQuery(
            createdAfterDate = Clock.System.now().minus(DateTimePeriod(days = 1), TimeZone.UTC),
        ).execute(client)
        transactions.shouldNotBeNull()
        println("Got ${transactions.entities.size} transactions in the last day")
    }

    @Test
    fun `get most recent transaction details`() = runAuthedTest {
        val wallet = getCurrentWallet()
        val transactions = wallet.getTransactionsQuery(
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
    fun `create and decode a payment request`() = runAuthedTest {
        val paymentRequest = client.createInvoice(
            42000,
            "Pizza!",
        )

        println("encoded invoice: ${paymentRequest.encodedPaymentRequest}")

        val decoded = client.decodeInvoice(paymentRequest.encodedPaymentRequest)
        decoded.shouldNotBeNull()
        println("decoded invoice: $decoded")
    }

    @Test
    fun `send a payment for an invoice`() = runAuthedTest {
        client.loadWalletSigningKey(WALLET_SIGNING_PRIV_KEY!!.decodeBase64Bytes())

        // Just paying a pre-existing AMP invoice.
        val ampInvoice =
            "lnbcrt1pjr8xwypp5xqj2jfpkz095s8zu57ktsq8vt8yazwcmqpcke9pvl67ne9cpdr0qdqj2a5xzumnwd6hqurswqcqzpgxq9z0rgqs" +
                "p55hfn0caa5sexea8u979cckkmwelw6h3zpwel5l8tn8s0elgwajss9q8pqqqssqefmmw79tknhl5xhnh7yfepzypxknwr9r4ya7" +
                "ueqa6vz20axvys8se986hwj6gppeyzst44hm4yl04c4dqjjpqgtt0df254q087sjtfsq35yagj"
        val payment = client.payInvoice(ampInvoice, maxFeesMsats = 100_000, amountMsats = 10_000)
        payment.shouldNotBeNull()
        println("Payment: $payment")
    }

    // TODO: Add tests for withdrawals and deposits.

    private suspend fun getCurrentWallet(): Wallet {
        val wallet = client.getCurrentWallet()
        wallet.shouldNotBeNull()
        return wallet
    }
}
