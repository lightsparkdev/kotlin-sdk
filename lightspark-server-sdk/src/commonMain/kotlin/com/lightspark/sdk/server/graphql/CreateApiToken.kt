package com.lightspark.sdk.server.graphql

import com.lightspark.sdk.server.model.ApiToken

const val CreateApiTokenMutation = """
    mutation CreateApiToken(
        ${'$'}name: String!
        ${'$'}permissions: [Permission!]!
    ) {
        create_api_token(input: {
            name: ${'$'}name
            permissions: ${'$'}permissions
        }) {
            create_api_token_output_api_token: api_token {
                ...ApiTokenFragment
            }
            create_api_token_output_client_secret: client_secret
        }
    }

${ApiToken.FRAGMENT}
"""
