//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkSyncWalletClient](index.md)/[terminateWallet](terminate-wallet.md)

# terminateWallet

[common]\
fun [terminateWallet](terminate-wallet.md)(): [Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)

Removes the wallet from Lightspark infrastructure. It won't be connected to the Lightning network anymore and its funds won't be accessible outside of the Funds Recovery Kit process.

#### Return

The wallet that was terminated.

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
