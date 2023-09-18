package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.ReleaseChannelPerCommitmentSecretOutput

const val ReleaseChannelPerCommitmentSecretMutation = """
mutation ReleaseChannelPerCommitmentSecret(
  ${'$'}channel_id: ID!
  ${'$'}per_commitment_secret: Hash32!
  ${'$'}per_commitment_index: Long!
) {
    release_channel_per_commitment_secret(input: {
		channel_id: ${'$'}channel_id
		per_commitment_secret: ${'$'}per_commitment_secret
        per_commitment_index: ${'$'}per_commitment_index
	}) {
        ...ReleaseChannelPerCommitmentSecretOutputFragment
    }
}

${ReleaseChannelPerCommitmentSecretOutput.FRAGMENT}
"""
