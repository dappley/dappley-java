package com.dappley.java.core.net;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Builder for ManagedChannel Class
 */
public class RpcChannelBuilder {
    private ManagedChannelBuilder channelBuilder;
    private static int MAX_CHANNEL_BYTES = 50 * 1024 * 1024;

    /**
     * Create a new channel
     * @param name host name
     * @param port server port
     * @return RpcChannelBuilder
     */
    public RpcChannelBuilder newChannel(String name, int port) {
        channelBuilder = ManagedChannelBuilder.forAddress(name, port)
                .idleTimeout(15, TimeUnit.SECONDS)
                .maxInboundMessageSize(MAX_CHANNEL_BYTES)
                .usePlaintext();
        return this;
    }

    /**
     * Return a instance of ManagedChannel
     * @return ManagedChannel
     */
    public ManagedChannel build() {
        return channelBuilder.build();
    }
}
