package com.lightspark.sdk.server.graphql

const val RecoverNodeSigningKeyQuery = """
  query RecoverNodeSigningKey(${'$'}nodeId: ID!) {
    entity(id: ${'$'}nodeId) {
      __typename
      ... on LightsparkNode {
        encrypted_signing_private_key {
          encrypted_value
          cipher
        }
      }
    }
  }
"""
