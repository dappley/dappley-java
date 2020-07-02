package com.dappley.java.core.net;

import com.dappley.java.core.po.ServerNode;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Builder for ManagedChannel Class
 */
public class RpcChannelBuilder {
    private ManagedChannelBuilder channelBuilder;
    private static int MAX_CHANNEL_BYTES = 50 * 1024 * 1024;

    /**
     * Create a new channel
     * @param serverNodes host address list
     * @return RpcChannelBuilder
     */
    public RpcChannelBuilder newChannel(ServerNode[] serverNodes) {
        List<SocketAddress> socketAddresses = Arrays.stream(serverNodes)
                .map(node -> new InetSocketAddress(node.getHost(), node.getPort()))
                .collect(Collectors.toList());
        NameResolver.Factory nameResolverFactory = new MultiAddressNameResolverFactory(socketAddresses);
        channelBuilder = ManagedChannelBuilder.forTarget("service")
                .nameResolverFactory(nameResolverFactory)
                .defaultLoadBalancingPolicy("pick_first")
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
