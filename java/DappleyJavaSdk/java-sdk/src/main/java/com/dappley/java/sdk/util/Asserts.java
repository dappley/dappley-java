package com.dappley.java.sdk.util;

import com.dappley.java.core.net.DataProvider;

/**
 * Predictions of variables
 */
public class Asserts {

    /**
     * Assert client is init
     * @param dataProvider
     * @throws IllegalStateException
     */
    public static void init(DataProvider dataProvider) {
        if (dataProvider == null) {
            throw new IllegalStateException("Dappley.init should be called first.");
        }
    }
}
