package com.dappley.android.sdk.net;

import android.content.Context;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.db.UtxoIndexDb;
import com.dappley.java.core.net.DataProvider;
import com.dappley.java.core.po.Block;
import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.po.UtxoIndex;
import com.dappley.java.core.util.ObjectUtils;
import com.tencent.mmkv.MMKV;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public void release() {
        MMKV.onExit();
    }

    @Override
    public List<Utxo> getUtxos(String address) {
        UtxoDb utxoDb = new UtxoDb(context);
        UtxoIndexDb utxoIndexDb = new UtxoIndexDb(context);
        Set<UtxoIndex> utxoIndexList = utxoIndexDb.get(address);
        if (ObjectUtils.isEmpty(utxoIndexList)) {
            return null;
        }
        List<Utxo> utxos = new ArrayList<>();
        Utxo utxo;
        for (UtxoIndex utxoIndex : utxoIndexList) {
            if (utxoIndex == null) {
                continue;
            }
            utxo = utxoDb.get(utxoIndex.toString());
            if (utxo == null) {
                continue;
            }
            utxos.add(utxo);
        }
        return utxos;
    }

    @Override
    public Block getBlockByHash(String hash) {
        BlockDb blockDb = new BlockDb(context);
        return blockDb.get(hash);
    }

    @Override
    public List<Block> getBlocks(List<String> startHashs, int count) {
        throw new IllegalStateException("local data mode do not support get blocks method.");
    }

    @Override
    public BigInteger getBalance(String address) {
        BigInteger balance = BigInteger.ZERO;
        if (ObjectUtils.isEmpty(address)) {
            return balance;
        }
        // compute from getUtxo method
        List<Utxo> utxos = getUtxos(address);
        if (ObjectUtils.isEmpty(utxos)) {
            return balance;
        }
        for (Utxo utxo : utxos) {
            if (utxo == null || utxo.getAmount() == null) {
                continue;
            }
            balance = balance.add(utxo.getAmount());
        }
        return balance;
    }
}
