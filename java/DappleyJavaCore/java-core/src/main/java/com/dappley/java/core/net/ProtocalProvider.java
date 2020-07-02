package com.dappley.java.core.net;

import com.dappley.java.core.po.BlockChainInfo;
import com.dappley.java.core.po.ContractQueryResult;
import com.dappley.java.core.po.ServerNode;
import com.dappley.java.core.po.SendTxResult;
import com.dappley.java.core.protobuf.BlockProto;
import com.dappley.java.core.protobuf.TransactionProto;
import com.dappley.java.core.protobuf.UtxoProto;
import com.google.protobuf.ByteString;

import java.util.List;

/**
 * Interfaces of protocal provider. Communicates with remote chain server.
 */
public interface ProtocalProvider {

    /**
     * Initialize provider
     * @param serverNodes
     */
    void init(ServerNode[] serverNodes);

    String getVersion();

    long getBalance(String address);

    BlockChainInfo getBlockchainInfo();

    List<UtxoProto.Utxo> getUtxo(String address);

    List<BlockProto.Block> getBlocks(List<ByteString> startHashs, int count);

    BlockProto.Block getBlockByHash(ByteString byteHash);

    BlockProto.Block getBlockByHeight(long height);

    SendTxResult sendTransaction(TransactionProto.Transaction transaction);

    ByteString estimateGas(TransactionProto.Transaction transaction);

    ByteString getGasPrice();

    ContractQueryResult contractQuery(String contractAddress, String key, String value);
}
