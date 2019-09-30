package com.dappley.java.sdk.demo;

import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

/**
 * Query current gas price in Dappley.
 * <p>Before call <code>Dappley.getWalletBalance</code>, call <code>Dappley.init</code> first.</p>
 */
@Slf4j
public class GetGasPrice {
    public static void main(String[] args) {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        BigInteger gasPrice = Dappley.getGasPrice();
        if (gasPrice != null) {
            log.info("gasPrice: " + gasPrice.toString());
        } else {
            log.info("getGasPrice failed");
        }
    }
}
