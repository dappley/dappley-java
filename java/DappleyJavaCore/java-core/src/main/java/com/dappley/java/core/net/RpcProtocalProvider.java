package com.dappley.java.core.net;

import com.dappley.java.core.po.BlockChainInfo;
import com.dappley.java.core.po.ContractQueryResult;
import com.dappley.java.core.po.SendTxResult;
import com.dappley.java.core.protobuf.*;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of ProtocalProvider on RPC protocal.
 */
@Slf4j
public class RpcProtocalProvider implements ProtocalProvider {

    private String serverIp;
    private int serverPort;

    @Override
    public void init(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    /**
     * Initialize rpc channel
     */
    private ManagedChannel openChannel() {
        RpcChannelBuilder channelBuilder = new RpcChannelBuilder().newChannel(serverIp, serverPort);
        ManagedChannel channel = channelBuilder.build();
        return channel;
    }

    /**
     * Close rpc connection
     * @param channel
     */
    private void shutdownChannel(ManagedChannel channel) {
        try {
            if (channel != null) {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getVersion() {
        ManagedChannel channel = openChannel();
        RpcProto.GetVersionRequest request = RpcProto.GetVersionRequest.newBuilder()
                .build();
        RpcProto.GetVersionResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcGetVersion(request);
        shutdownChannel(channel);

        String message = "[protocal version:" + response.getProtoVersion() + "] [server version: " + response.getServerVersion() + "]";
        log.debug("getVersion: " + message);
        return message;
    }

    @Override
    public long getBalance(String address) {
        ManagedChannel channel = openChannel();
        RpcProto.GetBalanceRequest request = RpcProto.GetBalanceRequest.newBuilder()
                .setAddress(address)
                .build();
        RpcProto.GetBalanceResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcGetBalance(request);
        shutdownChannel(channel);

        log.debug("getBalance: " + response.getAmount());
        return response.getAmount();
    }

    @Override
    public BlockChainInfo getBlockchainInfo() {
        ManagedChannel channel = openChannel();
        RpcProto.GetBlockchainInfoRequest request = RpcProto.GetBlockchainInfoRequest.newBuilder()
                .build();
        RpcProto.GetBlockchainInfoResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcGetBlockchainInfo(request);
        shutdownChannel(channel);

        BlockChainInfo blockChainInfo = new BlockChainInfo();
        blockChainInfo.setTailBlockHash(response.getTailBlockHash());
        blockChainInfo.setBlockHeight(response.getBlockHeight());
        blockChainInfo.setProducers(response.getProducersList());
        log.debug("getBlockchainInfo: " + response.toString());
        return blockChainInfo;
    }

    @Override
    public List<UtxoProto.Utxo> getUtxo(String address) {
        ManagedChannel channel = openChannel();
        RpcProto.GetUTXORequest request = RpcProto.GetUTXORequest.newBuilder()
                .setAddress(address)
                .build();
        RpcProto.GetUTXOResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcGetUTXO(request);
        shutdownChannel(channel);

        List<UtxoProto.Utxo> utxos = response.getUtxosList();
        return utxos;
    }

    @Override
    public List<BlockProto.Block> getBlocks(List<ByteString> startHashs, int count) {
        ManagedChannel channel = openChannel();
        RpcProto.GetBlocksRequest request = RpcProto.GetBlocksRequest.newBuilder()
                .addAllStartBlockHashes(startHashs)
                .setMaxCount(count)
                .build();
        RpcProto.GetBlocksResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcGetBlocks(request);
        shutdownChannel(channel);

        log.debug("getBlocks blockCount" + response.getBlocksCount());
        return response.getBlocksList();
    }

    @Override
    public BlockProto.Block getBlockByHash(ByteString byteHash) {
        ManagedChannel channel = openChannel();
        RpcProto.GetBlockByHashRequest request = RpcProto.GetBlockByHashRequest.newBuilder()
                .setHash(byteHash)
                .build();
        RpcProto.GetBlockByHashResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcGetBlockByHash(request);
        shutdownChannel(channel);

        BlockProto.Block block = response.getBlock();
        return block;
    }

    @Override
    public BlockProto.Block getBlockByHeight(long height) {
        ManagedChannel channel = openChannel();
        RpcProto.GetBlockByHeightRequest request = RpcProto.GetBlockByHeightRequest.newBuilder()
                .setHeight(height)
                .build();
        RpcProto.GetBlockByHeightResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcGetBlockByHeight(request);
        shutdownChannel(channel);

        BlockProto.Block block = response.getBlock();
        return block;
    }

    @Override
    public SendTxResult sendTransaction(TransactionProto.Transaction transaction) {
        ManagedChannel channel = openChannel();
        RpcProto.SendTransactionRequest request = RpcProto.SendTransactionRequest.newBuilder()
                .setTransaction(transaction)
                .build();
        SendTxResult sendTxResult = new SendTxResult();
        sendTxResult.setSuccess(false);
        try {
            RpcProto.SendTransactionResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcSendTransaction(request);
            shutdownChannel(channel);

            sendTxResult.setSuccess(true);
            sendTxResult.setGeneratedContractAddress(response.getGeneratedContractAddress());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sendTxResult;
    }

    @Override
    public ByteString estimateGas(TransactionProto.Transaction transaction) {
        ManagedChannel channel = openChannel();
        RpcProto.EstimateGasRequest request = RpcProto.EstimateGasRequest.newBuilder()
                .setTransaction(transaction)
                .build();
        RpcProto.EstimateGasResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcEstimateGas(request);
        shutdownChannel(channel);

        ByteString gasCount = response.getGasCount();
        return gasCount;
    }

    @Override
    public ByteString getGasPrice() {
        ManagedChannel channel = openChannel();
        RpcProto.GasPriceRequest request = RpcProto.GasPriceRequest.newBuilder()
                .build();
        RpcProto.GasPriceResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcGasPrice(request);
        shutdownChannel(channel);
        ByteString gasPrice = response.getGasPrice();
        return gasPrice;
    }

    @Override
    public ContractQueryResult contractQuery(String contractAddress, String key, String value) {
        ManagedChannel channel = openChannel();
        RpcProto.ContractQueryRequest.Builder builder = RpcProto.ContractQueryRequest.newBuilder();
        builder.setContractAddr(contractAddress);
        if (key != null) {
            builder.setKey(key);
        }
        if (value != null) {
            builder.setValue(value);
        }
        RpcProto.ContractQueryRequest request = builder.build();
        RpcProto.ContractQueryResponse response = RpcServiceGrpc.newBlockingStub(channel).rpcContractQuery(request);
        shutdownChannel(channel);

        ContractQueryResult result = new ContractQueryResult();
        result.setResultKey(response.getKey());
        result.setResultValue(response.getValue());
        return result;
    }
}
