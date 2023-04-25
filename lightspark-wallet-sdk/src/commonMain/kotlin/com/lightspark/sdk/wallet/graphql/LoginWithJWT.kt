package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.LoginWithJWTOutput

const val LoginWithJWT = """
  query LoginWithJWT(${'$'}account_id: ID!, ${'$'}jwt: String!) {
    login_with_jwt(input: { account_id: ${'$'}account_id, jwt: ${'$'}jwt }) {
        ...LoginWithJWTOutputFragment
    }
  }

${LoginWithJWTOutput.FRAGMENT}
"""
