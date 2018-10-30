package com.dappley.android.sdk.db;

import android.content.Context;

import com.dappley.android.sdk.po.BlockChainInfo;
import com.dappley.android.sdk.util.SerializeUtil;
import com.tencent.mmkv.MMKV;

import java.util.HashSet;
import java.util.Set;

/**
 * Database for BlockChain data.
 * <p>Base on MMKV key-value database. Constructor with context also create a mmkv database object.</p>
 * <p>Values would be temporary saved in cache memory and persisted into disk automatically.</p>
 */
public class BlockChainDb {
    private static final String DB_NAME = "block_chain";
    private static final String KEY_CURRENT_HASH = "current_hash";
    private static final String KEY_GENESIS_HASH = "genesis_hash";
    private static final String KEY_BLOCK_CHAIN_INFO = "block_chain_info";
    private static final String KEY_WALLET_ADDRESSES = "wallet_address";

    private Context context;
    private MMKV mmkv;

    /**
     * Constructor
     * @param context
     */
    public BlockChainDb(Context context) {
        this.context = context;
        mmkv = MMKV.mmkvWithID(DB_NAME);
    }

    /**
     * Save current hash to database.
     * @param hashString object
     * @return boolean true/false
     */
    public boolean saveCurrentHash(String hashString) {
        boolean success = false;
        try {
            mmkv.encode(KEY_CURRENT_HASH, hashString);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Read current hash from database.
     * @return String current hash string.
     */
    public String getCurrentHash() {
        try {
            return mmkv.decodeString(KEY_CURRENT_HASH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save genesis hash to database.
     * @param hashString object
     * @return boolean true/false
     */
    public boolean saveGenesisHash(String hashString) {
        boolean success = false;
        try {
            mmkv.encode(KEY_GENESIS_HASH, hashString);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Read genesis hash from database.
     * @return String genesis hash string.
     */
    public String getGenesisHash() {
        try {
            return mmkv.decodeString(KEY_GENESIS_HASH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save Block chain info to database.
     * @param blockChainInfo object
     * @return boolean true/false
     */
    public boolean saveBlockChainInfo(BlockChainInfo blockChainInfo) {
        boolean success = false;
        try {
            mmkv.encode(KEY_BLOCK_CHAIN_INFO, blockChainInfo.toByteArray());
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Read block chain info from database.
     * @return BlockChainInfo
     */
    public BlockChainInfo getBlockChainInfo() {
        try {
            byte[] bytes = mmkv.decodeBytes(KEY_BLOCK_CHAIN_INFO);
            return BlockChainInfo.parseBytes(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save user's wallet address into db.
     * @param walletAddress address in base58 string format
     * @return boolean true/false
     */
    public boolean saveWalletAddress(String walletAddress) {
        Set<String> walletAddressSet = getWalletAddressSet();
        if (walletAddressSet == null) {
            walletAddressSet = new HashSet<>(1);
        }
        return saveWalletAddressSet(walletAddressSet);
    }

    /**
     * Save a set of user's wallet address into db.
     * @param walletAddressSet several addresses
     * @return boolean true/false
     */
    public boolean saveWalletAddressSet(Set<String> walletAddressSet) {
        boolean success = false;
        try {
            byte[] bytes = SerializeUtil.encode(walletAddressSet);
            mmkv.encode(KEY_WALLET_ADDRESSES, bytes);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Returns all user's address info list from database
     * @return Set<String> wallet address set in base68 format
     */
    public Set<String> getWalletAddressSet() {
        try {
            byte[] bytes = mmkv.decodeBytes(KEY_WALLET_ADDRESSES);
            return SerializeUtil.decode(bytes, Set.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
