package com.dappley.android.sdk.util;

import android.content.Context;

import io.grpc.ManagedChannel;

/**
 * Predictions of variables
 */
public class Asserts {

    /**
     * Assert channel has been initialized
     * @param channel RPC protocol channel
     * @throws IllegalAccessException
     */
    public static void clientInit(ManagedChannel channel) {
        if (channel == null) {
            throw new IllegalStateException("RpcProvider.init should be called first.");
        }
    }

    /**
     * Assert channel is on open state
     * @param channel RPC protocol channel
     * @throws IllegalStateException
     */
    public static void channelOpen(ManagedChannel channel) {
        if (channel == null || channel.isShutdown() || channel.isTerminated()) {
            throw new IllegalStateException("channel is not opened.");
        }
    }

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
