package com.dappley.android.sdk.net;

import android.content.Context;

import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.BlockChainInfo;
import com.dappley.android.sdk.protobuf.BlockProto;
import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.protobuf.TransactionProto;
import com.google.protobuf.ByteString;

import java.util.List;

/**
 * Interfaces of protocal provider. Communicates with remote chain server.
 */
public interface ProtocalProvider {

    /**
     * Initialize provider
     * @param context
     */
    void init(Context context);

    /**
     * Close connections and free memories
     */
    void close();

    String getVersion();

    String getBalance(String address);

    String addBalance(String address);

    BlockChainInfo getBlockchainInfo();

    List<RpcProto.UTXO> getUtxo(String address);

    List<BlockProto.Block> getBlocks(List<ByteString> startHashs, int count);

    BlockProto.Block getBlockByHash(ByteString byteHash);

    BlockProto.Block getBlockByHeight(long height);

    int sendTransaction(TransactionProto.Transaction transaction);

    /**
     * Define types of network protocal supported.
     */
    enum ProviderType {
        /**
         * RPC Protocal
         */
        RPC,
        /**
         * Http Protocal
         */
        HTTP
    }
}
