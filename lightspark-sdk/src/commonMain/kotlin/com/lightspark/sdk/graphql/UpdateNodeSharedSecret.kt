package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UpdateNodeSharedSecretOutput

const val UpdateNodeSharedSecretMutation = """
mutation UpdateNodeSharedSecret(
  ${'$'}node_id : ID!
  ${'$'}shared_secret : Hash32!
) {
    update_node_shared_secret(input: {
		node_id: ${'$'}node_id
		shared_secret: ${'$'}shared_secret
	}) {
        ...UpdateNodeSharedSecretOutputFragment
    }
}

${UpdateNodeSharedSecretOutput.FRAGMENT}
"""
