package com.dappley.java.sdk.demo;

import com.dappley.java.core.po.Wallet;
import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

/**
 * Create wallet address demo.
 */
@Slf4j
public class WalletCreate {

    public static void main(String[] args) {
        Wallet wallet = Dappley.createWallet();
        log.info(wallet.toString());
    }
}
