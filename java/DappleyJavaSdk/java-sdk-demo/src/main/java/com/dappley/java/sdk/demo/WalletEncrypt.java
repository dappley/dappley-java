package com.dappley.java.sdk.demo;

import com.dappley.java.core.po.Wallet;
import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

/**
 * Encrypt wallet data in order to save it on the disk safely. We also provide decrypt method in the same time.
 */
@Slf4j
public class WalletEncrypt {
    public static void main(String[] args) {
        String mnemonic = "figure light wasp oil buzz method comic jewel mammal vendor ceiling adapt";
        Wallet wallet = Dappley.importWalletFromMnemonic(mnemonic);
        wallet = Dappley.encryptWallet(wallet, "123456");
        log.info("encrypted wallet: " + wallet.toString());
        wallet = Dappley.decryptWallet(wallet, "123456");
        log.info("decrypted wallet: " + wallet.toString());
    }
}
