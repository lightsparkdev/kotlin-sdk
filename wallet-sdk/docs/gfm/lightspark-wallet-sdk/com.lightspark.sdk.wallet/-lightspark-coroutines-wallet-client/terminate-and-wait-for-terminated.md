//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[terminateAndWaitForTerminated](terminate-and-wait-for-terminated.md)

# terminateAndWaitForTerminated

[common]\
suspend fun [terminateAndWaitForTerminated](terminate-and-wait-for-terminated.md)(): Flow&lt;[Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)&gt;

Removes the wallet from Lightspark infrastructure. It won't be connected to the Lightning network anymore and its funds won't be accessible outside of the Funds Recovery Kit process.

This waits for the wallet to be either `TERMINATED`or `FAILED`. It will periodically fire updates until then.

#### Return

The wallet updates until it is terminated.

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
