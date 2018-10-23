package com.dappley.android.sdk.net;

import android.content.Context;

import com.dappley.android.sdk.config.Configuration;
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
    public String getVersion() throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);

        RpcProto.GetVersionRequest request = RpcProto.GetVersionRequest.newBuilder()
                .build();
        RpcProto.GetVersionResponse response = getRpcServiceBlockingStub().rpcGetVersion(request);
        String message = "[protocal version:" + response.getProtoVersion() + "] [server version: " + response.getServerVersion() + "]";
        return message;
    }

    @Override
    public String getBalance(String address) throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);

        RpcProto.GetBalanceRequest request = RpcProto.GetBalanceRequest.newBuilder()
                .setAddress(address)
                .build();
        RpcProto.GetBalanceResponse response = getRpcServiceBlockingStub().rpcGetBalance(request);
        String message = response.getMessage();
        return message;
    }

    @Override
    public String addBalance(String address) throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);
        RpcProto.AddBalanceRequest request = RpcProto.AddBalanceRequest.newBuilder()
                .setAddress(address)
                .build();
        RpcProto.AddBalanceResponse response = getRpcServiceBlockingStub().rpcAddBalance(request);
        String message = response.getMessage();
        return message;
    }

    @Override
    public void getBlockchainInfo() throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);
        RpcProto.GetBlockchainInfoRequest request = RpcProto.GetBlockchainInfoRequest.newBuilder()
                .build();
        RpcProto.GetBlockchainInfoResponse response = getRpcServiceBlockingStub().rpcGetBlockchainInfo(request);
        System.out.println("getBlockchainInfo blockHeight: " + response.getBlockHeight());
        ByteString byteString = response.getTailBlockHash();
        System.out.println(byteString);
    }

    @Override
    public List<RpcProto.UTXO> getUtxo(String address) throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);
        RpcProto.GetUTXORequest request = RpcProto.GetUTXORequest.newBuilder()
                .setAddress(address)
                .build();
        RpcProto.GetUTXOResponse response = getRpcServiceBlockingStub().rpcGetUTXO(request);
        System.out.println("getUtxo errorCode: " + response.getErrorCode());
        List<RpcProto.UTXO> utxos = response.getUtxosList();
        return utxos;
    }

    @Override
    public void getBlocks() throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);
        RpcProto.GetBlocksRequest request = RpcProto.GetBlocksRequest.newBuilder()
//                .setStartBlockHashs(0, null)
                .build();
        RpcProto.GetBlocksResponse response = getRpcServiceBlockingStub().rpcGetBlocks(request);
        response.getBlocksList();
        System.out.println("getBlocks blockCount: " + response.getBlocksCount());
    }

    @Override
    public void getBlockByHash(ByteString byteHash) throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);
        RpcProto.GetBlockByHashRequest request = RpcProto.GetBlockByHashRequest.newBuilder()
                .setHash(byteHash)
                .build();
        RpcProto.GetBlockByHashResponse response = getRpcServiceBlockingStub().rpcGetBlockByHash(request);
        BlockProto.Block block = response.getBlock();
        System.out.println("getBlockByHash block: " + block);
    }

    @Override
    public BlockProto.Block getBlockByHeight(long height) throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);
        RpcProto.GetBlockByHeightRequest request = RpcProto.GetBlockByHeightRequest.newBuilder()
                .setHeight(height)
                .build();
        RpcProto.GetBlockByHeightResponse response = getRpcServiceBlockingStub().rpcGetBlockByHeight(request);
        BlockProto.Block block = response.getBlock();
        System.out.println("getBlockByHeight block: " + block);
        return block;
    }

    @Override
    public int sendTransaction(TransactionProto.Transaction transaction) throws IllegalAccessException {
        Asserts.clientInit(channel);
        Asserts.channalOpen(channel);
        RpcProto.SendTransactionRequest request = RpcProto.SendTransactionRequest.newBuilder()
                .setTransaction(transaction)
                .build();
        RpcProto.SendTransactionResponse response = getRpcServiceBlockingStub().rpcSendTransaction(request);
        int errorCode = response.getErrorCode();
        System.out.println("sendTransaction errorCode: " + errorCode);
        return errorCode;
    }
}
