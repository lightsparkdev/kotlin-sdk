package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.Account

const val CurrentAccountQuery = """
query CurrentAccount {
    current_account {
        ...AccountFragment
    }
}

${Account.FRAGMENT}
"""
