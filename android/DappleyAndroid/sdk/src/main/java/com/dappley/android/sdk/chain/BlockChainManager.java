package com.dappley.android.sdk.chain;

import android.content.Context;
import android.util.ArraySet;

import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.util.HexUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Utils of BlockChain service.
 */
public class BlockChainManager {
    /**
     * Genesis block's hash
     */
    private static String genesisHash;

    /**
     * Initialize genesis block and save into block db.
     * @param context
     */
    public static void initGenesisBlock(Context context) {
        BlockChainDb blockChainDb = new BlockChainDb(context);
        BlockDb blockDb = new BlockDb(context);
        if (StringUtils.isNotEmpty(blockChainDb.getCurrentHash())
                && blockDb.get(blockChainDb.getCurrentHash()) != null) {
            return;
        }
        Block block = BlockManager.newGenesisBlock();
        if (block == null || block.getHeader() == null || block.getHeader().getHash() == null) {
            return;
        }
        String genesisHash = HexUtil.toHex(block.getHeader().getHash());
        blockChainDb.saveGenesisHash(genesisHash);
        blockChainDb.saveCurrentHash(genesisHash);
        blockDb.save(block);
    }

    /**
     * Returns the genesis hash value of chain
     * @param context
     * @return String genesis hash value
     */
    public static String getGenesisHash(Context context) {
        if (StringUtils.isNotEmpty(genesisHash)) {
            return genesisHash;
        }
        // first query from db
        BlockChainDb blockChainDb = new BlockChainDb(context);
        genesisHash = blockChainDb.getGenesisHash();
        if (StringUtils.isNotEmpty(genesisHash)) {
            return genesisHash;
        }
        // init genesis block
        initGenesisBlock(context);
        genesisHash = blockChainDb.getGenesisHash();
        return genesisHash;
    }

    /**
     * Add a wallet address into system db.
     * @param context
     * @param walletAddress user's wallet address
     */
    public static void addWalletAddress(Context context, String walletAddress) {
        if (StringUtils.isEmpty(walletAddress)) {
            return;
        }
        BlockChainDb blockChainDb = new BlockChainDb(context);
        Set<String> walletAddressSet = blockChainDb.getWalletAddressSet();
        if (walletAddressSet == null) {
            walletAddressSet = new HashSet<>();
        }
        if (walletAddressSet.contains(walletAddress)) {
            return;
        }
        // form related utxos
        UtxoManager.buildUserUtxo(context, walletAddress);

        blockChainDb.saveWalletAddress(walletAddress);
    }

    /**
     * Remove a wallet address into system db.
     * @param context
     * @param walletAddress user's wallet address
     */
    public static void removeWalletAddress(Context context, String walletAddress) {
        if (StringUtils.isEmpty(walletAddress)) {
            return;
        }
        BlockChainDb blockChainDb = new BlockChainDb(context);
        Set<String> walletAddressSet = blockChainDb.getWalletAddressSet();
        if (walletAddressSet == null) {
            walletAddressSet = new HashSet<>();
        }
        if (!walletAddressSet.contains(walletAddress)) {
            return;
        }
        walletAddressSet.remove(walletAddress);

        // remove related utxos
        UtxoManager.removeUserUtxo(context, walletAddress);
        blockChainDb.saveWalletAddressSet(walletAddressSet);
    }
}
