package com.dappley.android.sdk.db;

import android.content.Context;

import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.po.UtxoIndex;
import com.dappley.android.sdk.util.HexUtil;
import com.dappley.android.sdk.util.SerializeUtil;
import com.tencent.mmkv.MMKV;

import java.util.HashSet;
import java.util.Set;

/**
 * Database for utxo index. Key is user's wallet address, and the value is a utxo set.
 * <p>Base on MMKV key-value database. Constructor with context also create a mmkv database object.</p>
 * <p>Values would be temporary saved in cache memory and persisted into disk automatically.</p>
 */
public class UtxoIndexDb {
    private static final String DB_NAME = "utxo_index";
    private Context context;
    private MMKV mmkv;

    /**
     * Constructor
     * @param context
     */
    public UtxoIndexDb(Context context) {
        this.context = context;
        mmkv = MMKV.mmkvWithID(DB_NAME);
    }

    /**
     * Returns the UtxoIndex set belong to current address
     * @param address wallet address
     * @return Set<UtxoIndex>
     */
    public Set<UtxoIndex> get(String address) {
        try {
            byte[] bytes = mmkv.decodeBytes(address);
            return SerializeUtil.decode(bytes, Set.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save utxo index set.
     * @param address wallet address
     * @param utxoIndexSet index set
     */
    public void save(String address, Set<UtxoIndex> utxoIndexSet) {
        byte[] bytes = SerializeUtil.encode(utxoIndexSet);
        mmkv.encode(address, bytes);
    }

    /**
     * Save a uxto index data.
     * @param address wallet address
     * @param utxoIndex obj
     */
    public void save(String address, UtxoIndex utxoIndex) {
        Set<UtxoIndex> utxoIndexSet = get(address);
        if (utxoIndexSet == null) {
            utxoIndexSet = new HashSet<>(1);
        }
        utxoIndexSet.add(utxoIndex);
        save(address, utxoIndexSet);
    }

    /**
     * Save a utxo index data.
     * @param address wallet address
     * @param txId transaction id
     * @param voutIndex transaction vout index
     */
    public void save(String address, byte[] txId, int voutIndex) {
        save(address, new UtxoIndex(HexUtil.toHex(txId), voutIndex));
    }

    /**
     * Remove utxo index.
     * @param address wallet address
     */
    public void remove(String address) {
        mmkv.removeValueForKey(address);
    }

    /**
     * Remove a utxo index value from utxoIndexSet
     * @param address wallet address
     * @param utxoIndex a specified value
     */
    public void remove(String address, UtxoIndex utxoIndex) {
        Set<UtxoIndex> utxoIndexSet = get(address);
        if (utxoIndexSet == null) {
            return;
        }
        utxoIndexSet.remove(utxoIndex);
        save(address, utxoIndexSet);
    }

    /**
     * Remove a utxo index value from utxoIndexSet
     * @param address wallet address
     * @param txId transaction id
     * @param voutIndex transaction vout index
     */
    public void remove(String address, byte[] txId, int voutIndex) {
        remove(address, new UtxoIndex(HexUtil.toHex(txId), voutIndex));
    }
}
