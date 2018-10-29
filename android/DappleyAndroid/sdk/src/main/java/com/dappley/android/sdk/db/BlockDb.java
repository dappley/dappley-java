package com.dappley.android.sdk.db;

import android.content.Context;

import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.util.HexUtil;
import com.tencent.mmkv.MMKV;

import java.util.List;
import java.util.Map;

/**
 * Database for Block class.
 * <p>Base on MMKV key-value database. Constructor with context also create a mmkv database object.</p>
 * <p>Values would be temporary saved in cache memory and persisted into disk automatically.</p>
 */
public class BlockDb {
    private static final String DB_NAME = "block";
    private Context context;
    private MMKV mmkv;

    /**
     * Constructor
     * @param context
     */
    public BlockDb(Context context) {
        this.context = context;
        mmkv = MMKV.mmkvWithID(DB_NAME);
    }

    /**
     * Save object data to database.
     * <p>Only save simple block info, not contains transaction list data.</p>
     * @param block object
     * @return boolean true/false
     */
    public boolean save(Block block) {
        boolean success = false;
        try {
            List<Transaction> transactions = block.getTransactions();
            block.setTransactions(null);
            String key = HexUtil.toHex(block.getHeader().getHash());
            mmkv.encode(key, block.toByteArray());

            block.setTransactions(transactions);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Read object from database.
     * @param blockHash block hash value (key)
     * @return Block If key does not exits, returns null.
     */
    public Block get(byte[] blockHash) {
        return get(HexUtil.toHex(blockHash));
    }

    /**
     * Read object from database.
     * @param blockHash A String ID(Key)
     * @return Block If key does not exits, returns null.
     */
    public Block get(String blockHash) {
        try {
            byte[] bytes = mmkv.decodeBytes(blockHash);
            return Block.parseBytes(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Remove block from db
     * @param block
     */
    public void remove(Block block) {
        remove(HexUtil.toHex(block.getHeader().getHash()));
    }

    /**
     * Remove block from db by hash value
     * @param blockHash hash
     */
    public void remove(String blockHash) {
        try {
            mmkv.removeValueForKey(blockHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a set of blocks by hash keys
     * @param blockHashes hash keys
     */
    public void remove(String[] blockHashes) {
        try {
            mmkv.removeValuesForKeys(blockHashes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
