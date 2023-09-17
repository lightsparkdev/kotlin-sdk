package com.lightspark.javatest;

import static com.lightspark.sdk.core.crypto.RsaSigningKt.generateSigningKeyPair;
import static com.lightspark.sdk.core.util.PlatformKt.getPlatform;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.lightspark.sdk.core.crypto.RawRsaSigningKeyLoader;
import com.lightspark.sdk.wallet.ClientConfig;
import com.lightspark.sdk.wallet.LightsparkFuturesWalletClient;
import com.lightspark.sdk.wallet.auth.jwt.CustomJwtAuthProvider;
import com.lightspark.sdk.wallet.auth.jwt.InMemoryJwtStorage;
import com.lightspark.sdk.wallet.auth.jwt.JwtStorage;
import com.lightspark.sdk.wallet.model.KeyType;
import com.lightspark.sdk.wallet.model.LoginWithJWTOutput;
import com.lightspark.sdk.wallet.model.Wallet;
import com.lightspark.sdk.wallet.model.WalletStatus;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import kotlin.Unit;

public class LightsparkFuturesWalletClientTest {
    private String apiAccountId = getPlatform().getEnv("LIGHTSPARK_ACCOUNT_ID");
    private String apiJwt = getPlatform().getEnv("LIGHTSPARK_JWT");
    private String signingPubKey = getPlatform().getEnv("LIGHTSPARK_WALLET_PUB_KEY");
    private String signingPrivKey = getPlatform().getEnv("LIGHTSPARK_WALLET_PRIV_KEY");
    private JwtStorage jwtStorage = new InMemoryJwtStorage();
    private final ClientConfig config = new ClientConfig()
            .setAuthProvider(new CustomJwtAuthProvider(jwtStorage));
    private final LightsparkFuturesWalletClient client;

    LightsparkFuturesWalletClientTest() {
        String baseUrl = getPlatform().getEnv("LIGHTSPARK_EXAMPLE_BASE_URL");
        if (baseUrl != null) {
            config.setServerUrl(baseUrl);
        }
        client = new LightsparkFuturesWalletClient(config);
    }


    @Test
    void deployAndInitializeWallet() throws Exception {
        LoginWithJWTOutput output = client.loginWithJWT(apiAccountId, apiJwt, jwtStorage).get(5, TimeUnit.SECONDS);
        AtomicReference<Wallet> currentWallet = new AtomicReference<>(output.getWallet());
        if (currentWallet.get().getStatus() == WalletStatus.NOT_SETUP || currentWallet.get().getStatus() == WalletStatus.TERMINATED) {
            client.deployWalletAndAwaitDeployed(wallet -> {
                        System.out.println("Wallet update: " + wallet.getStatus());
                        assertNotEquals(wallet.getStatus(), WalletStatus.FAILED);
                        if (wallet.getStatus() == WalletStatus.DEPLOYED) {
                            System.out.println("Wallet deployed!");
                        }
                        currentWallet.set(wallet);
                        return Unit.INSTANCE;
                    }
            ).get(30, TimeUnit.SECONDS);
        }

        if (currentWallet.get().getStatus() == WalletStatus.DEPLOYED) {
            KeyPair keypair = generateSigningKeyPair();
            signingPubKey = Base64.getEncoder().encodeToString(keypair.getPublic().getEncoded());
            signingPrivKey = Base64.getEncoder().encodeToString(keypair.getPrivate().getEncoded());
            System.out.println("Save these keys:");
            System.out.println(signingPubKey);
            System.out.println(signingPrivKey);

            client.loadWalletSigningKey(new RawRsaSigningKeyLoader(keypair.getPrivate().getEncoded()));
            client.initializeWalletAndWaitForInitialized(KeyType.RSA_OAEP, signingPubKey, signingPrivKey, wallet -> {
                        System.out.println("Wallet update: " + wallet.getStatus());
                        assertNotEquals(wallet.getStatus(), WalletStatus.FAILED);
                        if (wallet.getStatus() == WalletStatus.READY) {
                            System.out.println("Wallet initialized!");
                        }
                        currentWallet.set(wallet);
                        return Unit.INSTANCE;
                    }
            ).get(5, TimeUnit.MINUTES);
        }

        System.out.println("Post-initialized wallet:" + currentWallet.get());
    }
}
