package com.dappley.android.sdk.net;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Builder for ManagedChannel Class
 */
public class RpcChannelBuilder {
    private ManagedChannelBuilder channelBuilder;

    /**
     * Create a new channel
     * @param name host name
     * @param port server port
     * @return RpcChannelBuilder
     */
    public RpcChannelBuilder newChannel(String name, int port) {
        channelBuilder = ManagedChannelBuilder.forAddress(name, port)
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
