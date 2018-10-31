package com.dappley.android.sdk.db;

import android.content.Context;

import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.util.HexUtil;
import com.tencent.mmkv.MMKV;

/**
 * Database for Utxo data.
 * <p>Base on MMKV key-value database. Constructor with context also create a mmkv database object.</p>
 * <p>Values would be temporary saved in cache memory and persisted into disk automatically.</p>
 * <p>Transaction's id and vount index is used for key and utxo is for value. </p>
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
            String key = HexUtil.toHex(utxo.getTxId()) + "-" + utxo.getVoutIndex();
            mmkv.encode(key, utxo.toByteArray());
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Read object from database.
     * @param txId transaction id
     * @param voutIndex vout index
     * @return Utxo If key does not exits, returns null.
     */
    public Utxo get(byte[] txId, int voutIndex) {
        return get(HexUtil.toHex(txId), voutIndex);
    }

    /**
     * Read object from database.
     * @param txId A String ID
     * @param voutIndex vout index
     * @return Utxo If key does not exits, returns null.
     */
    public Utxo get(String txId, int voutIndex) {
        return get(txId + "-" + voutIndex);
    }

    /**
     * Read object from database
     * @param utxoIndex utxo index to string value
     * @return Utxo object data
     */
    public Utxo get(String utxoIndex) {
        try {
            byte[] bytes = mmkv.decodeBytes(utxoIndex);
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            return Utxo.parseBytes(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Remove Utxo from db.
     * @param txId transaction id
     * @param voutIndex vout index
     */
    public void remove(byte[] txId, int voutIndex) {
        remove(HexUtil.toHex(txId), voutIndex);
    }

    /**
     * Remove Utxo from db.
     * @param txId transaction id
     * @param voutIndex vout index
     */
    public void remove(String txId, int voutIndex) {
        remove(txId + "-" + voutIndex);
    }

    /**
     *  Remove Utxo from db.
     * @param utxoIndex utxo index to string value
     */
    public void remove(String utxoIndex) {
        mmkv.removeValueForKey(utxoIndex);
    }

    /**
     * Remove Utxos from db.
     * @param keys transaction id and voutIndex array
     */
    public void remove(String[] keys) {
        mmkv.removeValuesForKeys(keys);
    }

    /**
     * Clear all data
     */
    public void clearAll() {
        mmkv.clearAll();
    }
}
