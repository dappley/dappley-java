package com.dappley.android.sdk.task;

import android.content.Context;
import android.util.Log;

import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.db.TransactionDb;
import com.dappley.android.sdk.net.DataProvider;
import com.dappley.android.sdk.net.RemoteDataProvider;
import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.util.HexUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Synchronize block datas from online node
 */
public class LocalBlockThread implements Runnable {
    private static final String TAG = "LocalBlockThread";
    private static final int PREV_COUNT = 7;
    private static final int ONCE_COUNT = 200;
    private Context context;
    private DataProvider dataProvider;
    private BlockDb blockDb;
    private TransactionDb transactionDb;
    private BlockChainDb blockChainDb;

    public LocalBlockThread(Context context) {
        this.context = context;
        blockDb = new BlockDb(context);
        blockChainDb = new BlockChainDb(context);
        transactionDb = new TransactionDb(context);
        // TODO get a provider
        dataProvider = new RemoteDataProvider(context, RemoteDataProvider.RemoteProtocalType.RPC);

        Log.i(TAG, "LocalBlockThread created");
    }

    @Override
    public void run() {
        Log.i(TAG, "run at time " + System.currentTimeMillis());
        List<String> startHashes = getStartHashes();
        if (CollectionUtils.isEmpty(startHashes)) {
            return;
        }
        try {
            List<Block> blocks = dataProvider.getBlocks(startHashes, ONCE_COUNT);
            // save blocks to db
            saveBlocks(blocks);
        } catch (Exception e) {
            Log.e(TAG, "run: ", e);
        }
    }

    /**
     * Forms start hash values
     * <p>From current block to PREV_COUNT previous block. Add each hash value into startHashes.</p>
     * <p>Hash in list should be ranged in decrease mode.
     *    That means the tail block hash should be list[0].
     * </p>
     * @return List<String> formed hash list
     */
    private List<String> getStartHashes() {
        List<String> startHashes = new ArrayList<>();
        String currentHash = blockChainDb.getCurrentHash();
        if (StringUtils.isEmpty(currentHash)) {
            return null;
        }
        Block block = blockDb.get(currentHash);
        if (block == null) {
            return null;
        }
        startHashes.add(currentHash);
        // traverse PREV_COUNT block data
        for (int i = 1; i < PREV_COUNT; i++) {
            if (block.getHeader().getPrevHash() == null
                    || StringUtils.isEmpty(HexUtil.toHex(block.getHeader().getPrevHash()))) {
                break;
            }
            block = blockDb.get(block.getHeader().getPrevHash());
            if (block == null) {
                break;
            }
            startHashes.add(HexUtil.toHex(block.getHeader().getHash()));
        }
        Log.i(TAG, "getStartHashes start hash count: " + startHashes.size());
        return startHashes;
    }

    /**
     * Save block datas into db
     * <p>Save each block data into BlockDb.
     *    RPC protocal blocks is ranged in increase mode. That means the last one in block list is the tail block.
     *    Save transactions data into TransactionDb while BlockDb do not contains transactions info.
     *    Update current hash value in BlockChainDb for next use.
     * </p>
     * @param blocks
     */
    private void saveBlocks(List<Block> blocks) {
        if (CollectionUtils.isEmpty(blocks)) {
            return;
        }
        Log.i(TAG, "saveBlocks count " + blocks.size());
        String currentHash = null;
        for (Block block : blocks) {
            currentHash = HexUtil.toHex(block.getHeader().getHash());
            blockDb.save(block);
            transactionDb.save(currentHash, block.getTransactions());
        }
        // update current hash value
        blockChainDb.saveCurrentHash(currentHash);
        // TODO handler fork
    }

}
