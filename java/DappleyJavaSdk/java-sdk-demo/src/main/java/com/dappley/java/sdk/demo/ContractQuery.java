package com.dappley.java.sdk.demo;

import com.dappley.java.core.po.ContractQueryResult;
import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

/**
 * Query values storaged by contract address.
 * <p>Before call <code>Dappley.getWalletBalance</code>, call <code>Dappley.init</code> first.</p>
 */
@Slf4j
public class ContractQuery {
    public static void main(String[] args) {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String contractAddress = "cWU5kmWy6iXVPkgvpegdLzS9ahinouUnue";
        String key = "file-name";
        String value = "SHA-1-sign";
        ContractQueryResult contractQueryResult = Dappley.contractQuery(contractAddress, key, null);
        log.info("contractQuery1: resultKey: " + contractQueryResult.getResultKey() + ",resultValue:" + contractQueryResult.getResultValue());
        contractQueryResult = Dappley.contractQuery(contractAddress, null, value);
        log.info("contractQuery2: resultKey: " + contractQueryResult.getResultKey() + ",resultValue:" + contractQueryResult.getResultValue());
        contractQueryResult = Dappley.contractQuery(contractAddress, key, value);
        log.info("contractQuery3: resultKey: " + contractQueryResult.getResultKey() + ",resultValue:" + contractQueryResult.getResultValue());
    }
}
