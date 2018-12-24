package com.dappley.java.sdk.demo;

import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

/**
 * Validate the address is correct.
 */
@Slf4j
public class ValidateAddress {
    public static void main(String[] args) {
        String address = "dZMupn1PaT2yJW9t9BFJjHMszWXwKWmBzX";
        boolean isAddress = Dappley.validateAddress(address);
        log.info("isAddress: " + isAddress);

        String contractAddress = "cXuhH7BZKHuMAGtLkqyzGZWSBBSWqm19KY";
        boolean isContractAddress = Dappley.validateContractAddress(contractAddress);
        log.info("isContractAddress: " + isContractAddress);
    }
}
