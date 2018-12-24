package com.dappley.java.sdk.demo;

import com.dappley.java.core.po.Wallet;
import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Query wallet balances by wallet list. Data should be formed as an ArrayList with wallet entity, and the wallet address is valid.
 * <p>Before call <code>Dappley.getWalletBalances</code>, call <code>Dappley.init</code> first.</p>
 */
@Slf4j
public class GetBalances {
    public static void main(String[] args) {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        List<Wallet> walletList = new ArrayList<>(2);
        walletList.add(Dappley.createWallet());
        walletList.add(Dappley.createWallet());
        walletList = Dappley.getWalletBalances(walletList);
        log.info("walletList: " + walletList.toString());
    }
}
