package com.dappley.java.core.net;

import com.dappley.java.core.po.ServerNode;

/**
 * Builder of protocal provider
 */
public class ProtocalProviderBuilder {
    private RemoteDataProvider.RemoteProtocalType type;
    private ServerNode[] serverNodes;

    /**
     * Register of RemoteProtocalType. RemoteProtocalType.RPC is supported now.
     * @param type RemoteProtocalType
     * @return ProtocalProviderBuilder
     */
    public ProtocalProviderBuilder setType(RemoteDataProvider.RemoteProtocalType type) {
        this.type = type;
        return this;
    }

    /**
     * Set rpc server nodes
     * @param serverNodes
     * @return ProtocalProviderBuilder
     */
    public ProtocalProviderBuilder setServerNodes(ServerNode[] serverNodes) {
        this.serverNodes = serverNodes;
        return this;
    }

    /**
     * Build a ProtocalProvider
     * @return ProtocalProvider
     */
    public ProtocalProvider build() {
        ProtocalProvider protocalProvider;
        if (type == RemoteDataProvider.RemoteProtocalType.RPC) {
            protocalProvider = new RpcProtocalProvider();
            protocalProvider.init(serverNodes);
        } else {
            throw new IllegalArgumentException("only rpc protocal is supported now.");
        }
        return protocalProvider;
    }
}
