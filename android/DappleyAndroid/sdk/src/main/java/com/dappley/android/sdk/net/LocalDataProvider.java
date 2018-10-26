package com.dappley.android.sdk.net;

import android.content.Context;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.protobuf.BlockProto;
import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.protobuf.TransactionProto;
import com.dappley.android.sdk.util.HashUtil;
import com.google.protobuf.ByteString;
import com.tencent.mmkv.MMKV;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provide block chain datas from local storage.
 */
public class LocalDataProvider implements DataProvider {
    private Context context;

    public LocalDataProvider(Context context) {
        this.context = context;
        // initialize MMKV database instance
        MMKV.initialize(context);

        BlockChainManager.initGenesisBlock(context);
    }

    @Override
    public List<Utxo> getUtxos(String address) {
        byte[] pubKeyHash = HashUtil.getPubKeyHash(address);
        UtxoDb utxoDb = new UtxoDb(context);
//        String[] txIds = utxoDb.getAllKeys();
//        if (ArrayUtils.isEmpty(txIds)) {
//            return null;
//        }
        List<Utxo> utxos = new ArrayList<>();
//        Utxo utxo;
//        for (String txId : txIds) {
//            utxo = utxoDb.get(txId);
//            if (utxo == null) {
//                continue;
//            }
//            utxos.add(utxo);
//        }
        return utxos;
    }

    @Override
    public Block getBlockByHash(String hash) {
        BlockDb blockDb = new BlockDb(context);
        return blockDb.get(hash);
    }

    @Override
    public List<Block> getBlocks(List<String> startHashs, int count) {
        // TODO getblocks

        return null;
    }
}
