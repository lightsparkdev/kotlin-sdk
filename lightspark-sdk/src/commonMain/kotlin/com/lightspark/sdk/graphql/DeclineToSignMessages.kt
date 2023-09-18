package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.DeclineToSignMessagesOutput

const val DeclineToSignMessagesMutation = """
mutation DeclineToSignMessages(${'$'}payload_ids: [ID!]!) {
	decline_to_sign_messages(input: {
		payload_ids: ${'$'}payload_ids
	}) {
		...DeclineToSignMessagesOutputFragment
	}
}

${DeclineToSignMessagesOutput.FRAGMENT}
"""
