package com.dappley.android.sdk.util;

import android.content.Context;

/**
 * Predictions of variables
 */
public class Asserts {

    /**
     * Assert client is init
     * @param context
     * @throws IllegalStateException
     */
    public static void init(Context context) {
        if (context == null) {
            throw new IllegalStateException("Dappley.init should be called first.");
        }
    }
}
