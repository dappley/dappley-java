package com.dappley.android.sdk.task;

import android.content.Context;
import android.util.Log;

import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.db.BlockIndexDb;
import com.dappley.android.sdk.db.TransactionDb;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.net.DataProvider;
import com.dappley.android.sdk.net.RemoteDataProvider;
import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.util.HexUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    private BlockIndexDb blockIndexDb;
    private TransactionDb transactionDb;
    private BlockChainDb blockChainDb;
    private UtxoDb utxoDb;

    public LocalBlockThread(Context context) {
        this.context = context;
        blockDb = new BlockDb(context);
        blockChainDb = new BlockChainDb(context);
        transactionDb = new TransactionDb(context);
        utxoDb = new UtxoDb(context);
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
            saveBlocks(blocks, startHashes);
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
     * @param blocks new tail blocks
     * @param startHashs old tail blocks
     */
    private void saveBlocks(List<Block> blocks, List<String> startHashs) {
        if (CollectionUtils.isEmpty(blocks)) {
            return;
        }
        Log.i(TAG, "saveBlocks count " + blocks.size());

        // handler fork, remove invalid blocks
        String newParentHash = HexUtil.toHex(blocks.get(0).getHeader().getPrevHash());
        removeInvalidBlocks(newParentHash, startHashs);

        // save new datas
        String currentHash = null;
        for (Block block : blocks) {
            currentHash = HexUtil.toHex(block.getHeader().getHash());
            blockDb.save(block);
            blockIndexDb.save(HexUtil.toHex(block.getHeader().getPrevHash()), currentHash);
            transactionDb.save(currentHash, block.getTransactions());

            // TODO save new utxo data
        }
        // update current hash value
        blockChainDb.saveCurrentHash(currentHash);
    }

    /**
     * Remove invalid blocks from db
     * <p>Remove some invalid blocks when synchronized blocks from chain are not the same.</p>
     * <p>When the first hash equals to an old one, the process is stopped.</p>
     * @param newParentHash  the first block's hash of synchronized blocks from chain
     * @param oldHashes the last several block hash of chain tail in local db
     */
    private void removeInvalidBlocks(String newParentHash, List<String> oldHashes) {
        if (StringUtils.isEmpty(newParentHash) || CollectionUtils.isEmpty(oldHashes)) {
            return;
        }
        // form invalid block hash list
        List<String> invalidHashes = new ArrayList<>();
        for (String oldHash : oldHashes) {
            if (oldHash.equals(newParentHash)) {
                break;
            }
            // remove from db
            blockDb.remove(oldHash);
            blockIndexDb.remove(oldHash);
            invalidHashes.add(oldHash);
        }

        // remove transactions by invalid hashes
        removeInvalidTransactions(invalidHashes);
    }

    /**
     * Remove all invalid transactions from database.
     * @param blockHashes invalid block hash list
     */
    private void removeInvalidTransactions(List<String> blockHashes) {
        if (CollectionUtils.isEmpty(blockHashes)) {
            return;
        }
        List<Transaction> transactions;
        for (String blockHash : blockHashes) {
            transactions = transactionDb.get(blockHash);
            // remove values in utxo
            removeInvalidUtxo(transactions);

            transactionDb.remove(blockHash);
        }
    }

    /**
     * Remove all invalid utxo data from database.
     * @param transactions transaciton info list
     */
    private void removeInvalidUtxo(List<Transaction> transactions) {
        if (CollectionUtils.isEmpty(transactions)) {
            return;
        }
        for (Transaction transaction : transactions) {
            if (transaction == null) {
                continue;
            }
            utxoDb.remove(transaction.getId());
        }
    }
}
