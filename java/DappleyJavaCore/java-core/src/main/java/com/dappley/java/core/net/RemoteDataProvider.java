package com.dappley.java.core.net;

import com.dappley.java.core.po.Block;
import com.dappley.java.core.po.ContractQueryResult;
import com.dappley.java.core.po.Transaction;
import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.protobuf.BlockProto;
import com.dappley.java.core.protobuf.TransactionProto;
import com.dappley.java.core.protobuf.UtxoProto;
import com.dappley.java.core.util.HexUtil;
import com.dappley.java.core.util.ObjectUtils;
import com.google.protobuf.ByteString;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide block chain datas from online block node.
 */
public class RemoteDataProvider implements DataProvider {
    private ProtocalProvider protocalProvider;

    public RemoteDataProvider(RemoteProtocalType type, String serverIp, int serverPort) {
        if (type == RemoteProtocalType.RPC) {
            protocalProvider = new RpcProtocalProvider();
        } else {
            throw new IllegalArgumentException("only rpc protocal is supported now.");
        }
        protocalProvider.init(serverIp, serverPort);
    }

    @Override
    public void release() {
        if (protocalProvider != null) {
            protocalProvider.close();
        }
    }

    @Override
    public List<Utxo> getUtxos(String address) {
        try {
            List<UtxoProto.Utxo> utxoList = protocalProvider.getUtxo(address);
            if (utxoList == null) {
                return null;
            }
            // format data
            List<Utxo> utxos = new ArrayList<>(utxoList.size());
            Utxo utxo;
            for (UtxoProto.Utxo u : utxoList) {
                utxo = new Utxo(u);
                utxos.add(utxo);
            }
            return utxos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Block getBlockByHash(String hash) {
        try {
            BlockProto.Block blockProto = protocalProvider.getBlockByHash(ByteString.copyFrom(HexUtil.toBytes(hash)));
            // format data
            return new Block(blockProto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Block> getBlocks(List<String> startHashs, int count) {
        if (startHashs == null || count <= 0) {
            return null;
        }
        try {
            // format data
            List<ByteString> startHashStrings = new ArrayList<>(startHashs.size());
            ByteString byteString;
            for (String hash : startHashs) {
                byteString = ByteString.copyFrom(HexUtil.toBytes(hash));
                startHashStrings.add(byteString);
            }
            List<BlockProto.Block> blockList = protocalProvider.getBlocks(startHashStrings, count);
            if (blockList == null) {
                return null;
            }
            List<Block> blocks = new ArrayList<>(blockList.size());
            Block block;
            for (BlockProto.Block b : blockList) {
                block = new Block(b);
                blocks.add(block);
            }
            return blocks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BigInteger getBalance(String address) {
        BigInteger balance = BigInteger.ZERO;
        if (ObjectUtils.isEmpty(address)) {
            return balance;
        }
        // compute from getUtxo method
        List<UtxoProto.Utxo> utxos = protocalProvider.getUtxo(address);
        if (ObjectUtils.isEmpty(utxos)) {
            return balance;
        }
        for (UtxoProto.Utxo utxo : utxos) {
            if (utxo == null || utxo.getAmount() == null || utxo.getAmount().size() == 0) {
                continue;
            }
            balance = balance.add(new BigInteger(1, utxo.getAmount().toByteArray()));
        }
        return balance;
    }

    @Override
    public BigInteger estimateGas(Transaction transaction) {
        if (transaction == null) {
            return BigInteger.ZERO;
        }
        TransactionProto.Transaction tx = transaction.toProto();
        ByteString count = protocalProvider.estimateGas(tx);
        BigInteger gasCount = BigInteger.ZERO;
        if (count != null && count.size() > 0) {
            gasCount = new BigInteger(1, count.toByteArray());
        }
        return gasCount;
    }

    @Override
    public BigInteger getGasPrice() {
        ByteString price = protocalProvider.getGasPrice();
        BigInteger gasPrice = BigInteger.ZERO;
        if (price != null && price.size() > 0) {
            gasPrice = new BigInteger(1, price.toByteArray());
        }
        return gasPrice;
    }

    @Override
    public ContractQueryResult contractQuery(String contractAddress, String key, String value) {
        ContractQueryResult result = protocalProvider.contractQuery(contractAddress, key, value);
        return result;
    }

    /**
     * Define types of network protocal supported.
     */
    public enum RemoteProtocalType {
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
