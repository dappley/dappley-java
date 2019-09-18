package com.dappley.java.sdk.demo;

import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

/**
 * Send a transaction between two user addresses.
 * <p>Before call <code>Dappley.sendTransaction</code>, call <code>Dappley.init</code> first.</p>
 */
@Slf4j
public class SendTransaction {
    public static void main(String[] args) {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String from = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        String to = "dW9JqNxJ4T39MmTPx5imuS1LopsGzrXj9X";
        BigInteger amount = new BigInteger("1");
        BigInteger tip = new BigInteger("1");
        BigInteger privateKey = new BigInteger("300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a", 16);
        boolean isSuccess = Dappley.sendTransaction(from, to, amount, privateKey, tip);
        log.info("sendTransaction isSuccess:" + isSuccess);
    }
}
