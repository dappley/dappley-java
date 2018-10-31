package com.dappley.android.sdk.chain;

import android.content.Context;

import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.util.HexUtil;

import org.apache.commons.lang3.StringUtils;

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
     *
     * @param context
     * @param walletAddress
     */
    public static void addWalletAddress(Context context, String walletAddress) {
        BlockChainDb blockChainDb = new BlockChainDb(context);
        blockChainDb.saveWalletAddress(walletAddress);
    }
}
