package com.dappley.android.sdk.net;

import android.content.Context;
import android.util.Log;

import com.dappley.android.sdk.config.Configuration;
import com.dappley.android.sdk.po.BlockChainInfo;
import com.dappley.android.sdk.protobuf.BlockProto;
import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.protobuf.RpcServiceGrpc;
import com.dappley.android.sdk.protobuf.TransactionProto;
import com.dappley.android.sdk.util.Asserts;
import com.google.protobuf.ByteString;

import java.util.List;

import io.grpc.ManagedChannel;

/**
 * Implementation of ProtocalProvider on RPC protocal.
 */
public class RpcProvider implements ProtocalProvider {
    private static final String TAG = "RpcProvider";

    private static ManagedChannel channel;
    private static RpcServiceGrpc.RpcServiceBlockingStub rpcServiceBlockingStub;

    @Override
    public void init(Context context) {
        RpcChannelBuilder channelBuilder = new RpcChannelBuilder()
                .newChannel(Configuration.getInstance(context).getServerIp(), Configuration.getInstance(context).getServerPort());
        channel = channelBuilder.build();
    }

    /**
     * Returns the singleton of RpcServiceBlockingStub
     * @return RpcServiceBlockingStub
     */
    private static RpcServiceGrpc.RpcServiceBlockingStub getRpcServiceBlockingStub() {
        if (rpcServiceBlockingStub == null) {
            rpcServiceBlockingStub = RpcServiceGrpc.newBlockingStub(channel);
        }
        return rpcServiceBlockingStub;
    }

    @Override
    public void close() {
        if (channel == null) {
            return;
        }
        channel.shutdown();
    }

    @Override
    public String getVersion() {
        Asserts.clientInit(channel);
        Asserts.channelOpen(channel);

        RpcProto.GetVersionRequest request = RpcProto.GetVersionRequest.newBuilder()
                .build();
        RpcProto.GetVersionResponse response = getRpcServiceBlockingStub().rpcGetVersion(request);
        String message = "[protocal version:" + response.getProtoVersion() + "] [server version: " + response.getServerVersion() + "]";
        Log.d(TAG, "getVersion: " + message);
        return message;
    }

    @Override
    public String getBalance(String address) {
        Asserts.clientInit(channel);
        Asserts.channelOpen(channel);

        RpcProto.GetBalanceRequest request = RpcProto.GetBalanceRequest.newBuilder()
                .setAddress(address)
                .build();
        RpcProto.GetBalanceResponse response = getRpcServiceBlockingStub().rpcGetBalance(request);
        String message = response.getMessage();
        Log.d(TAG, "getBalance: " + response.getAmount());
        return message;
    }

    @Override
    public BlockChainInfo getBlockchainInfo() {
        Asserts.clientInit(channel);
        Asserts.channelOpen(channel);
        RpcProto.GetBlockchainInfoRequest request = RpcProto.GetBlockchainInfoRequest.newBuilder()
                .build();
        RpcProto.GetBlockchainInfoResponse response = getRpcServiceBlockingStub().rpcGetBlockchainInfo(request);
        BlockChainInfo blockChainInfo = new BlockChainInfo();
        blockChainInfo.setTailBlockHash(response.getTailBlockHash());
        blockChainInfo.setBlockHeight(response.getBlockHeight());
        blockChainInfo.setProducers(response.getProducersList());
        Log.d(TAG, "getBlockchainInfo: " + response.toString());
        return blockChainInfo;
    }

    @Override
    public List<RpcProto.UTXO> getUtxo(String address) {
        Asserts.clientInit(channel);
        Asserts.channelOpen(channel);
        RpcProto.GetUTXORequest request = RpcProto.GetUTXORequest.newBuilder()
                .setAddress(address)
                .build();
        RpcProto.GetUTXOResponse response = getRpcServiceBlockingStub().rpcGetUTXO(request);
        List<RpcProto.UTXO> utxos = response.getUtxosList();
        Log.d(TAG, "getUtxo errorCode: " + response.getErrorCode());
        return utxos;
    }

    @Override
    public List<BlockProto.Block> getBlocks(List<ByteString> startHashs, int count) {
        Asserts.clientInit(channel);
        Asserts.channelOpen(channel);
        RpcProto.GetBlocksRequest request = RpcProto.GetBlocksRequest.newBuilder()
                .addAllStartBlockHashes(startHashs)
                .setMaxCount(count)
                .build();
        RpcProto.GetBlocksResponse response = getRpcServiceBlockingStub().rpcGetBlocks(request);
        Log.d(TAG, "getBlocks blockCount" + response.getBlocksCount());
        return response.getBlocksList();
    }

    @Override
    public BlockProto.Block getBlockByHash(ByteString byteHash) {
        Asserts.clientInit(channel);
        Asserts.channelOpen(channel);
        RpcProto.GetBlockByHashRequest request = RpcProto.GetBlockByHashRequest.newBuilder()
                .setHash(byteHash)
                .build();
        RpcProto.GetBlockByHashResponse response = getRpcServiceBlockingStub().rpcGetBlockByHash(request);
        BlockProto.Block block = response.getBlock();
        Log.d(TAG, "getBlockByHash errorCode: " + response.getErrorCode());
        return block;
    }

    @Override
    public BlockProto.Block getBlockByHeight(long height) {
        Asserts.clientInit(channel);
        Asserts.channelOpen(channel);
        RpcProto.GetBlockByHeightRequest request = RpcProto.GetBlockByHeightRequest.newBuilder()
                .setHeight(height)
                .build();
        RpcProto.GetBlockByHeightResponse response = getRpcServiceBlockingStub().rpcGetBlockByHeight(request);
        BlockProto.Block block = response.getBlock();
        Log.d(TAG, "getBlockByHeight errorCode: " + response.getErrorCode());
        return block;
    }

    @Override
    public int sendTransaction(TransactionProto.Transaction transaction) {
        Asserts.clientInit(channel);
        Asserts.channelOpen(channel);
        RpcProto.SendTransactionRequest request = RpcProto.SendTransactionRequest.newBuilder()
                .setTransaction(transaction)
                .build();
        RpcProto.SendTransactionResponse response = getRpcServiceBlockingStub().rpcSendTransaction(request);
        int errorCode = response.getErrorCode();
        Log.d(TAG, "sendTransaction errorCode: " + errorCode);
        return errorCode;
    }
}
