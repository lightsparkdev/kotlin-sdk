package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.SignMessagesOutput

const val SignMessagesMutation = """
mutation SignMessages(
  ${'$'}signatures: [IdAndSignature!]!
) {
    sign_messages(input: {
		signatures: ${'$'}signatures
	}) {
        ...SignMessagesOutputFragment
    }
}

${SignMessagesOutput.FRAGMENT}
"""
