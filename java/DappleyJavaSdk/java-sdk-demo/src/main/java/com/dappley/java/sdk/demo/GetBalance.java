package com.dappley.java.sdk.demo;

import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

/**
 * Query wallet balance by wallet address.
 * <p>Before call <code>Dappley.getWalletBalance</code>, call <code>Dappley.init</code> first.</p>
 */
@Slf4j
public class GetBalance {
    public static void main(String[] args) {
        String address = "dZMupn1PaT2yJW9t9BFJjHMszWXwKWmBzX";
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        BigInteger balance = Dappley.getWalletBalance(address);
        log.info("balance: " + balance.toString());
    }
}
