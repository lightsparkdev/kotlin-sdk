package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UpdateChannelPerCommitmentPointOutput

const val UpdateChannelPerCommitmentPointMutation = """
mutation UpdateChannelPerCommitmentPoint(
  ${'$'}channel_id : ID!
  ${'$'}per_commitment_point : PublicKey!
  ${'$'}per_commitment_point_index : Long!
) {
    update_channel_per_commitment_point(input: {
		channel_id: ${'$'}channel_id
        per_commitment_point_index: ${'$'}per_commitment_point_index
		per_commitment_point: ${'$'}per_commitment_point
	}) {
        ...UpdateChannelPerCommitmentPointOutputFragment
    }
}

${UpdateChannelPerCommitmentPointOutput.FRAGMENT}
"""
