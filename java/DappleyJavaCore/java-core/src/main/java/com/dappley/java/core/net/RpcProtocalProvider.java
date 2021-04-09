package com.dappley.java.core.net;

import com.dappley.java.core.po.BlockChainInfo;
import com.dappley.java.core.po.ContractQueryResult;
import com.dappley.java.core.po.SendTxResult;
import com.dappley.java.core.po.ServerNode;
import com.dappley.java.core.protobuf.*;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of ProtocalProvider on RPC protocal.
 */
@Slf4j
public class RpcProtocalProvider implements ProtocalProvider {
    /**
     * timeout seconds of each RPC request
     */
    private int timeout = 15;
    private ServerNode[] serverNodes;
    private ServerNode serverNodeCache;
    private int ConnectNodeModel;

    @Override
    public void init(ServerNode[] serverNodes) {
        this.serverNodes = serverNodes;
    }

    /**
     * Set RPC request timeout
     * @param timeout value in seconds
     */
    public void setRequestTimeoutSeconds(int timeout) {
        if (timeout <= 0) {
            return;
        }
        this.timeout = timeout;
    }

    /**
     * Initialize rpc channel
     */
    private ManagedChannel openChannel() {
        if (serverNodes.length == 0){
            return null;
        }
        ServerNode[] serverNodesInput = new ServerNode[1];
        serverNodesInput[0] = new ServerNode();
        switch (ConnectNodeModel){
            case 1://改变之前的连接
                if(serverNodeCache == null){
                    serverNodesInput[0] = serverNodes[0];
                    serverNodeCache =  serverNodesInput[0];
                }else {
                    for(int i=0;i< serverNodes.length;i++){
                        if (serverNodeCache == serverNodes[i]){
                            if (i+1 == serverNodes.length){
                                serverNodesInput[0] = serverNodes[0];
                            }else {
                                serverNodesInput[0] = serverNodes[(i+1)];
                            }
                            serverNodeCache = serverNodesInput[0];
                            break;
                        }
                    }
                }
                break;
            default:
                if(serverNodeCache == null){
                    serverNodesInput[0] = serverNodes[0];
                    serverNodeCache = serverNodesInput[0];
                }else {
                    serverNodesInput[0] = serverNodeCache;
                }
        }
        log.info("openChannel host:"+serverNodeCache.getHost());
        RpcChannelBuilder channelBuilder = new RpcChannelBuilder().newChannel(serverNodesInput);
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
                channel.shutdown().awaitTermination(this.timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns new blocking stub with timeout value
     * @param channel
     * @return
     */
    private AdminServiceGrpc.AdminServiceBlockingStub getAdminBlockingStub(ManagedChannel channel) {
        return AdminServiceGrpc.newBlockingStub(channel).withDeadlineAfter(this.timeout, TimeUnit.SECONDS);
    }

    /**
     * Returns new blocking stub with timeout value
     * @param channel
     * @return
     */
    private RpcServiceGrpc.RpcServiceBlockingStub getBlockingStub(ManagedChannel channel) {
        return RpcServiceGrpc.newBlockingStub(channel).withDeadlineAfter(this.timeout, TimeUnit.SECONDS);
    }

    @Override
    public String getVersion() {
        ManagedChannel channel = openChannel();
        RpcProto.GetVersionRequest request = RpcProto.GetVersionRequest.newBuilder()
                .build();
        shutdownChannel(channel);
        String message = null;
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.GetVersionResponse response = getBlockingStub(channel).rpcGetVersion(request);
                message = "[protocal version:" + response.getProtoVersion() + "] [server version: " + response.getServerVersion() + "]";
                log.debug("getVersion: " + message);
                shutdownChannel(channel);
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }

        return message;
    }

    @Override
    public long getBalance(String address) {
        ManagedChannel channel = openChannel();
        RpcProto.GetBalanceRequest request = RpcProto.GetBalanceRequest.newBuilder()
                .setAddress(address)
                .build();
        long amount =0;
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.GetBalanceResponse response = getBlockingStub(channel).rpcGetBalance(request);
                amount =response.getAmount();
                shutdownChannel(channel);
            }catch(StatusRuntimeException e){
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }

        log.debug("getBalance: " ,amount);
        return amount;
    }

    @Override
    public BlockChainInfo getBlockchainInfo() {
        ManagedChannel channel = openChannel();
        RpcProto.GetBlockchainInfoRequest request = RpcProto.GetBlockchainInfoRequest.newBuilder()
                .build();
        BlockChainInfo blockChainInfo = new BlockChainInfo();
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.GetBlockchainInfoResponse response = getBlockingStub(channel).rpcGetBlockchainInfo(request);
                blockChainInfo.setTailBlockHash(response.getTailBlockHash());
                blockChainInfo.setBlockHeight(response.getBlockHeight());
                blockChainInfo.setProducers(response.getProducersList());
                log.debug("getBlockchainInfo:" + response.toString());
                shutdownChannel(channel);
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }

        return blockChainInfo;
    }

    @Override
    public List<UtxoProto.Utxo> getUtxo(String address) {
        List<UtxoProto.Utxo> utxos = new LinkedList<>();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        StreamObserver<RpcProto.GetUTXOResponse> responseObserver = new StreamObserver<RpcProto.GetUTXOResponse>() {
            private int cnt = 0;
            public void onNext(RpcProto.GetUTXOResponse response) {
                utxos.addAll(response.getUtxosList());
                log.info("getUtxo call back time:{}",++cnt);
            }

            public void onError(Throwable throwable) {
                log.warn("getUtxo call back error:{}",throwable.getMessage());
                countDownLatch.countDown();
            }

            public void onCompleted() {
                log.info("getUtxo call back complete");
                countDownLatch.countDown();
            }

        };
        ManagedChannel channel = openChannel();
        RpcProto.GetUTXORequest request = RpcProto.GetUTXORequest.newBuilder()
                .setAddress(address)
                .build();
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcServiceGrpc.RpcServiceStub rpcServiceStub = RpcServiceGrpc.newStub(channel);
                StreamObserver<RpcProto.GetUTXORequest> requestObserver = rpcServiceStub.rpcGetUTXO(responseObserver);
                while (true){
                    if ((utxos.size() % 1000) != 0){
                        break;
                    }
                    requestObserver.onNext(request);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //判断调用结束状态。如果整个调用已经结束，继续发送数据不会报错，但是会被舍弃
                    if(countDownLatch.getCount() == 0){
                        break;
                    }
                }
                requestObserver.onCompleted();
                try {
                    if(!countDownLatch.await(timeout, TimeUnit.MINUTES)){
                        log.warn("The getUtxo call did not complete within the specified time");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shutdownChannel(channel);
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }
        log.info("getUtxo call utxo list exit size:{}",utxos.size());
        return utxos;
    }

    @Override
    public List<BlockProto.Block> getBlocks(List<ByteString> startHashs, int count) {
        ManagedChannel channel = openChannel();
        RpcProto.GetBlocksRequest request = RpcProto.GetBlocksRequest.newBuilder()
                .addAllStartBlockHashes(startHashs)
                .setMaxCount(count)
                .build();
        List<BlockProto.Block> block = null;
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.GetBlocksResponse response = getBlockingStub(channel).rpcGetBlocks(request);
                block = response.getBlocksList();
                log.info("getBlocks blockCount" + response.getBlocksCount());
                shutdownChannel(channel);
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }

        return block;
    }

    @Override
    public BlockProto.Block getBlockByHash(ByteString byteHash) {
        ManagedChannel channel = openChannel();
        RpcProto.GetBlockByHashRequest request = RpcProto.GetBlockByHashRequest.newBuilder()
                .setHash(byteHash)
                .build();
        BlockProto.Block block = null;
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.GetBlockByHashResponse response = getBlockingStub(channel).rpcGetBlockByHash(request);
                block = response.getBlock();
                shutdownChannel(channel);
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }

        return block;
    }

    @Override
    public BlockProto.Block getBlockByHeight(long height) {
        ManagedChannel channel = openChannel();
        RpcProto.GetBlockByHeightRequest request = RpcProto.GetBlockByHeightRequest.newBuilder()
                .setHeight(height)
                .build();
        BlockProto.Block block = null;
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.GetBlockByHeightResponse response = getBlockingStub(channel).rpcGetBlockByHeight(request);
                block = response.getBlock();
                shutdownChannel(channel);
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }

        return block;
    }

    @Override
    public SendTxResult sendMinerTransaction(String toAddress, ByteString amount) {
        ManagedChannel channel = openChannel();
        RpcProto.SendFromMinerRequest request = RpcProto.SendFromMinerRequest.newBuilder()
                .setTo(toAddress)
                .setAmount(amount)
                .build();
        SendTxResult sendTxResult = new SendTxResult();
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.SendFromMinerResponse response = getAdminBlockingStub(channel).rpcSendFromMiner(request);
                shutdownChannel(channel);
                sendTxResult.setCode(SendTxResult.CODE_SUCCESS);
                //sendTxResult.setGeneratedContractAddress(response.getGeneratedContractAddress());
                break;
            } catch (Exception e) {
                sendTxResult.setCode(SendTxResult.CODE_ERROR_EXCEPTION);
                sendTxResult.setMsg(e.getMessage());
                log.error(e.getMessage());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }
        return sendTxResult;
    }

    @Override
    public SendTxResult sendTransaction(TransactionProto.Transaction transaction) {
        ManagedChannel channel = openChannel();
        RpcProto.SendTransactionRequest request = RpcProto.SendTransactionRequest.newBuilder()
                .setTransaction(transaction)
                .build();
        SendTxResult sendTxResult = new SendTxResult();
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.SendTransactionResponse response = getBlockingStub(channel).rpcSendTransaction(request);
                shutdownChannel(channel);

                sendTxResult.setCode(SendTxResult.CODE_SUCCESS);
                sendTxResult.setGeneratedContractAddress(response.getGeneratedContractAddress());
                break;
            } catch (Exception e) {
                sendTxResult.setCode(SendTxResult.CODE_ERROR_EXCEPTION);
                sendTxResult.setMsg(e.getMessage());
                log.error(e.getMessage());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }

        return sendTxResult;
    }

    @Override
    public ByteString estimateGas(TransactionProto.Transaction transaction) {
        ManagedChannel channel = openChannel();
        RpcProto.EstimateGasRequest request = RpcProto.EstimateGasRequest.newBuilder()
                .setTransaction(transaction)
                .build();
        ByteString gasCount = null;
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.EstimateGasResponse response = getBlockingStub(channel).rpcEstimateGas(request);
                gasCount = response.getGasCount();
                shutdownChannel(channel);
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }
        return gasCount;
    }

    @Override
    public ByteString getGasPrice() {
        ManagedChannel channel = openChannel();
        RpcProto.GasPriceRequest request = RpcProto.GasPriceRequest.newBuilder()
                .build();
        ByteString gasPrice = null;
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.GasPriceResponse response = getBlockingStub(channel).rpcGasPrice(request);
                gasPrice= response.getGasPrice();
                shutdownChannel(channel);
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }
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
        ContractQueryResult result = new ContractQueryResult();
        int length = serverNodes.length;
        while ((length--)>0){
            try {
                RpcProto.ContractQueryResponse response = getBlockingStub(channel).rpcContractQuery(request);
                shutdownChannel(channel);
                result.setResultKey(response.getKey());
                result.setResultValue(response.getValue());
                break;
            } catch (StatusRuntimeException e) {
                log.error("WARNING, RPC failed: Status=" + e.getStatus());
                ConnectNodeModel = 1;
                channel = openChannel();
                ConnectNodeModel = 0;
            }
        }

        return result;
    }
}
