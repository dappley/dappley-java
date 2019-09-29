package com.dappley.java.core.po;

import lombok.Data;

/**
 * Data returned by contract query request
 */
@Data
public class ContractQueryResult {
    /**
     * Query result key
     */
    private String resultKey;
    /**
     * Query result value
     */
    private String resultValue;
}
