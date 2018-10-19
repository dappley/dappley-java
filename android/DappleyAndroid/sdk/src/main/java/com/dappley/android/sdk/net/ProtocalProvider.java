package com.dappley.android.sdk.net;

import android.content.Context;

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

    String getVersion() throws IllegalAccessException;

    String getBalance(String address) throws IllegalAccessException;

    String addBalance(String address) throws IllegalAccessException;

    void getBlockchainInfo() throws IllegalAccessException;

    List<RpcProto.UTXO> getUtxo(String address) throws IllegalAccessException;

    void getBlocks() throws IllegalAccessException;

    void getBlockByHash(ByteString byteHash) throws IllegalAccessException;

    void getBlockByHeight(long height) throws IllegalAccessException;

    int sendTransaction(TransactionProto.Transaction transaction) throws IllegalAccessException;

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
