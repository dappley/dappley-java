package com.dappley.android.sdk.net;

import android.content.Context;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.protobuf.BlockProto;
import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.util.HexUtil;
import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide block chain datas from online block node.
 */
public class RemoteDataProvider implements DataProvider {
    private ProtocalProvider protocalProvider;
    private Context context;

    public RemoteDataProvider(Context context, RemoteProtocalType type) {
        this.context = context;
        if (type == RemoteProtocalType.RPC) {
            protocalProvider = new RpcProvider();
        } else {
            throw new IllegalArgumentException("only rpc protocal is supported now.");
        }

        BlockChainManager.initGenesisBlock(context);
    }

    @Override
    public List<Utxo> getUtxos(String address) {
        try {
            List<RpcProto.UTXO> utxoList = protocalProvider.getUtxo(address);
            if (utxoList == null) {
                return null;
            }
            // format data
            List<Utxo> utxos = new ArrayList<>(utxoList.size());
            Utxo utxo;
            for (RpcProto.UTXO u : utxoList) {
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
