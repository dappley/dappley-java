package com.dappley.java.sdk.net;

import com.dappley.java.core.net.DataProvider;
import com.dappley.java.core.po.Block;
import com.dappley.java.core.po.Utxo;

import java.math.BigInteger;
import java.util.List;

/**
 * Provide block chain datas from local storage.
 * <p>Not implement yet.</p>
 */
public class LocalDataProvider implements DataProvider {
    @Override
    public List<Utxo> getUtxos(String address) {
        return null;
    }

    @Override
    public Block getBlockByHash(String hash) {
        return null;
    }

    @Override
    public List<Block> getBlocks(List<String> startHashs, int count) {
        return null;
    }

    @Override
    public BigInteger getBalance(String address) {
        return null;
    }
}
