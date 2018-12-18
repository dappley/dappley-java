package com.dappley.java.core.util;

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
}
