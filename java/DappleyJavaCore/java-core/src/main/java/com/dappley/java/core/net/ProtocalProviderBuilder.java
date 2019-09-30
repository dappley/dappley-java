package com.dappley.java.core.net;

/**
 * Builder of protocal provider
 */
public class ProtocalProviderBuilder {
    private RemoteDataProvider.RemoteProtocalType type;
    private String rpcServerIp;
    private int rpcServerPort;

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
     * Set rpc server ip
     * @param rpcServerIp
     * @return ProtocalProviderBuilder
     */
    public ProtocalProviderBuilder setRpcServerIp(String rpcServerIp) {
        this.rpcServerIp = rpcServerIp;
        return this;
    }

    /**
     * Set rpc server port
     * @param rpcServerPort
     * @return ProtocalProviderBuilder
     */
    public ProtocalProviderBuilder setRpcServerPort(int rpcServerPort) {
        this.rpcServerPort = rpcServerPort;
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
            protocalProvider.init(rpcServerIp, rpcServerPort);
        } else {
            throw new IllegalArgumentException("only rpc protocal is supported now.");
        }
        return protocalProvider;
    }
}
