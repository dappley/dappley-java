package com.dappley.android.sdk.db;

import android.content.Context;

import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.util.HexUtil;
import com.tencent.mmkv.MMKV;

import org.bouncycastle.util.encoders.Hex;

import java.util.List;
import java.util.Map;

/**
 * Database for Utxo data.
 * <p>Base on MMKV key-value database. Constructor with context also create a mmkv database object.</p>
 * <p>Values would be temporary saved in cache memory and persisted into disk automatically.</p>
 * <p>Transaction's id is used for key and utxo is for value. </p>
 * <p>We would only save current user's utxo into database.
 *    If a new user is added into the wallet, related utxos would be newly saved by recalculating transactions.
 * </p>
 */
public class UtxoDb {
    private static final String DB_NAME = "utxo";
    private Context context;
    private MMKV mmkv;

    /**
     * Constructor
     * @param context
     */
    public UtxoDb(Context context) {
        this.context = context;
        mmkv = MMKV.mmkvWithID(DB_NAME);
    }

    /**
     * Save object data to database.
     * @param utxo object
     * @return boolean true/false
     */
    public boolean save(Utxo utxo) {
        boolean success = false;
        try {
            String key = HexUtil.toHex(utxo.getTxId());
            mmkv.encode(key, utxo.toByteArray());
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Read object from database.
     * @param txId transaction id (key)
     * @return Utxo If key does not exits, returns null.
     */
    public Utxo get(byte[] txId) {
        return get(HexUtil.toHex(txId));
    }

    /**
     * Read object from database.
     * @param txId A String ID(Key)
     * @return Block If key does not exits, returns null.
     */
    public Utxo get(String txId) {
        try {
            byte[] bytes = mmkv.decodeBytes(txId);
            return Utxo.parseBytes(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Remove Utxo from db.
     * @param txId transaction id
     */
    public void remove(byte[] txId) {
        remove(HexUtil.toHex(txId));
    }

    /**
     * Remove Utxo from db.
     * @param txId transaction id
     */
    public void remove(String txId) {
        mmkv.removeValueForKey(txId);
    }

    /**
     * Remove Utxos from db.
     * @param txIds transaction id array
     */
    public void remove(String[] txIds) {
        mmkv.removeValuesForKeys(txIds);
    }
}
