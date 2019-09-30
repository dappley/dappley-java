package com.dappley.java.sdk.demo;

import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

/**
 * Estimate gas consumption during contract execution. It doesn't cost any utxo.
 * <p>Before call <code>Dappley.getWalletBalance</code>, call <code>Dappley.init</code> first.</p>
 */
@Slf4j
public class EstimateGas {
    public static void main(String[] args) {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String from = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        String toContract = "cWU5kmWy6iXVPkgvpegdLzS9ahinouUnue";
        BigInteger privateKey = new BigInteger("300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a", 16);
        String contract = "{\"function\":\"put_sign\",\"args\":[\"key\",\"value\"]}";
        contract = String.format(contract, from, 10);
        BigInteger gasLimit = Dappley.estimateGas(from, privateKey, toContract, contract);
        if (gasLimit != null) {
            log.info("gasLimit: " + gasLimit.toString());
        } else {
            log.info("estimateGas failed");
        }
    }
}
