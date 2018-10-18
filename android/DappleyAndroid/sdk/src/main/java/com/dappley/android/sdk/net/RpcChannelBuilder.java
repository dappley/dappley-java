package com.dappley.android.sdk.net;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Builder for ManagedChannel Class
 */
public class RpcChannelBuilder {
    private ManagedChannelBuilder channelBuilder;

    public RpcChannelBuilder newChannel(String name, int port) {
        channelBuilder = ManagedChannelBuilder.forAddress(name, port)
                .usePlaintext();
        return this;
    }

    public ManagedChannel build() {
        return channelBuilder.build();
    }
}
