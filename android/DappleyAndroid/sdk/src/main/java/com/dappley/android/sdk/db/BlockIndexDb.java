package com.dappley.android.sdk.db;

import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * Database for block index. Key is previous hash, and the value is current hash.
 * <p>Base on MMKV key-value database. Constructor with context also create a mmkv database object.</p>
 * <p>Values would be temporary saved in cache memory and persisted into disk automatically.</p>
 */
public class BlockIndexDb {
    private static final String DB_NAME = "block_index";
    private Context context;
    private MMKV mmkv;

    /**
     * Constructor
     * @param context
     */
    public BlockIndexDb(Context context) {
        this.context = context;
        mmkv = MMKV.mmkvWithID(DB_NAME);
    }

    /**
     * Returns the next block hash base on current hash value
     * @param hash current hash
     * @return String nextHash hash
     */
    public String get(String hash) {
        return mmkv.getString(hash, "");
    }

    /**
     * Save hash index.
     * @param hash current hash
     * @param nextHash next hash value
     */
    public void save(String hash, String nextHash) {
        mmkv.putString(hash, nextHash);
    }

    /**
     * Remove hash index.
     * @param hash current hash
     */
    public void remove(String hash) {
        mmkv.removeValueForKey(hash);
    }
}
