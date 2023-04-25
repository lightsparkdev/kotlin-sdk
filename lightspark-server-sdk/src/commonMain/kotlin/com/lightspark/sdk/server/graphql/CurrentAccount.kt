package com.lightspark.sdk.server.graphql

import com.lightspark.sdk.server.model.Account

const val CurrentAccountQuery = """
query CurrentAccount {
    current_account {
        ...AccountFragment
    }
}

${Account.FRAGMENT}
"""
