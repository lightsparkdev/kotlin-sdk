package com.lightspark.sdk.graphql

const val ScreenNodeMutation = """
  mutation ScreenNode(
    ${'$'}provider: ComplianceProvider!
    ${'$'}node_pubkey: String!
) {
    screen_node(input: {
        provider: ${'$'}provider
        node_pubkey: ${'$'}node_pubkey
    }) {
        rating
    }
}
"""
