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
     * Initialize genesis block and save into block db.
     * @param context
     */
    public static void initGenesisBlock(Context context) {
        BlockChainDb blockChainDb = new BlockChainDb(context);
        if (StringUtils.isNotEmpty(blockChainDb.getCurrentHash())) {
            return;
        }
        Block block = BlockManager.newGenesisBlock();
        if (block == null || block.getHeader() == null || block.getHeader().getHash() == null) {
            return;
        }
        blockChainDb.saveCurrentHash(HexUtil.toHex(block.getHeader().getHash()));
        BlockDb blockDb = new BlockDb(context);
        blockDb.save(block);
    }
}
