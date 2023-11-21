package com.lightspark.sdk

import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.core.util.getPlatform
import com.lightspark.sdk.model.BitcoinNetwork
import com.lightspark.sdk.model.RegionCode
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class UmaInvitationIntegrationTests {
    // Read the token ID and secrets from the environment.
    private val API_TOKEN_CLIENT_ID = getPlatform().getEnv("LIGHTSPARK_API_TOKEN_CLIENT_ID")!!
    private val API_TOKEN_CLIENT_SECRET = getPlatform().getEnv("LIGHTSPARK_API_TOKEN_CLIENT_SECRET")!!

    private val config = ClientConfig()
        .setServerUrl(ServerEnvironment.DEV.graphQLUrl)
        .setAuthProvider(
            AccountApiTokenAuthProvider(
                API_TOKEN_CLIENT_ID,
                API_TOKEN_CLIENT_SECRET,
            ),
        )
        .setDefaultBitcoinNetwork(BitcoinNetwork.REGTEST)
    private val client = LightsparkCoroutinesClient(config)

    @Test
    fun `create invitation`() = runTest {
        val invitation = client.createUmaInvitation("bob@vasp1.com")
        invitation.shouldNotBeNull()
        println("Invitation: $invitation")
    }

    @Test
    fun `create invitation with incentives`() = runTest {
        val invitation = client.createUmaInvitationWithIncentives(
            "bob@vasp1.com", "+12345678900", RegionCode.US,
        )
        invitation.shouldNotBeNull()
        println("Invitation with incentives: $invitation")
    }

    @Test
    fun `create and claim invitation`() = runTest {
        val invitation = client.createUmaInvitation("bob@vasp1.com")
        invitation.shouldNotBeNull()
        val randomUma = "testuma-${System.currentTimeMillis()}@vasp2.com"
        val claimedInvitation = client.claimUmaInvitation(invitation.code, randomUma)
        claimedInvitation.shouldNotBeNull()
    }

    @Test
    fun `create and claim invitation with incentives`() = runTest {
        val invitation = client.createUmaInvitationWithIncentives(
            "bob@vasp1.com", "+12345678900", RegionCode.US,
        )
        invitation.shouldNotBeNull()
        val randomUma = "testuma-${System.currentTimeMillis()}@vasp2.com"
        val claimedInvitation = client.claimUmaInvitationWithIncentives(
            invitation.code, randomUma, "+12345678901", RegionCode.PH,
        )
        claimedInvitation.shouldNotBeNull()
    }

    @Test
    fun `create and fetch invitation`() = runTest {
        val invitation = client.createUmaInvitation("bob@vasp1.com")
        invitation.shouldNotBeNull()
        val fetchedInvitation = client.fetchUmaInvitation(invitation.code)
        fetchedInvitation shouldBeEqualToComparingFields invitation
    }

    @Test
    fun `test with invalid phone number`() = runTest {
        shouldThrow<IllegalArgumentException> {
            client.createUmaInvitationWithIncentives(
                "bob@vasp1.com", "+1234567890e", RegionCode.US,
            )
        }
    }
}
