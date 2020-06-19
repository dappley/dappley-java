package com.dappley.java.sdk.demo;

import com.dappley.java.core.po.SendTxResult;
import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

/**
 * Send a transaction with contract params.
 * <p>Before call <code>Dappley.sendTransactionWithContract</code>, call <code>Dappley.init</code> first.</p>
 */
@Slf4j
public class SendTransactionWithContract {
    public static void main(String[] args) {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String from = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        String toContract = "cXuhH7BZKHuMAGtLkqyzGZWSBBSWqm19KY";
        BigInteger fee = new BigInteger("1");
        BigInteger tip = new BigInteger("1");
        BigInteger gasLimit = new BigInteger("30000");
        BigInteger gasPrice = new BigInteger("1");
        BigInteger privateKey = new BigInteger("300c0338c4b0d49edc66113e3584e04c6b907f9ded711d396d522aae6a79be1a", 16);
        String contract = "{\"function\":\"record\",\"args\":[\"%s\",\"%d\"]}";
        contract = String.format(contract, from, 1);
        SendTxResult sendTxResult = Dappley.sendTransactionWithContract(from, toContract, fee, privateKey, tip, gasLimit, gasPrice, contract);
        if (sendTxResult != null) {
            log.info("sendTransactionWithContract isSuccess:" + (sendTxResult.getCode() == SendTxResult.CODE_SUCCESS));
            log.info("sendTransactionWithContract contractAddress:" + sendTxResult.getGeneratedContractAddress());
        } else {
            log.info("sendTransactionWithContract failed");
        }
    }
}
