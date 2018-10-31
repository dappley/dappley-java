package com.dappley.android.sdk.task;

import android.content.Context;
import android.util.Log;

import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.db.BlockIndexDb;
import com.dappley.android.sdk.db.TransactionDb;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.db.UtxoIndexDb;
import com.dappley.android.sdk.net.DataProvider;
import com.dappley.android.sdk.net.RemoteDataProvider;
import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.po.TxOutput;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.util.AddressUtil;
import com.dappley.android.sdk.util.HexUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Synchronized;

/**
 * Synchronize block datas from online node
 */
public class LocalBlockThread implements Runnable {
    private static final String TAG = "LocalBlockThread";
    private static final int PREV_COUNT = 7;
    private static final int ONCE_COUNT = 200;
    /**
     * sysn process tag
     */
    private static boolean isSync;

    private Context context;
    private DataProvider dataProvider;
    private BlockDb blockDb;
    private BlockIndexDb blockIndexDb;
    private TransactionDb transactionDb;
    private BlockChainDb blockChainDb;
    private UtxoDb utxoDb;
    private UtxoIndexDb utxoIndexDb;

    public LocalBlockThread(Context context) {
        this.context = context;
        blockDb = new BlockDb(context);
        blockIndexDb = new BlockIndexDb(context);
        blockChainDb = new BlockChainDb(context);
        transactionDb = new TransactionDb(context);
        utxoDb = new UtxoDb(context);
        utxoIndexDb = new UtxoIndexDb(context);
        // TODO get a provider
        dataProvider = new RemoteDataProvider(context, RemoteDataProvider.RemoteProtocalType.RPC);

        Log.i(TAG, "LocalBlockThread created");
    }

    /**
     * Thread run once. When the previous process is not finised, current process will be terminated.
     */
    @Override
    public void run() {
        if (isIsSync()) {
            // give up synchronize
            return;
        }
        Log.i(TAG, "run at time " + System.currentTimeMillis());
        List<String> startHashes = getStartHashes();
        if (CollectionUtils.isEmpty(startHashes)) {
            return;
        }
        try {
            // set sysn tag true
            setIsSync(true);
            List<Block> blocks = dataProvider.getBlocks(startHashes, ONCE_COUNT);
            // save blocks to db
            saveBlocks(blocks, startHashes);

            // set sysn tag false
            setIsSync(false);
        } catch (Exception e) {
            Log.e(TAG, "run: ", e);
        }
    }

    /**
     * Returns if there is a synchronize process.
     * @return
     */
    public static boolean isIsSync() {
        return isSync;
    }

    /**
     * Set synchronize tag.
     * @param isSync true/false
     */
    @Synchronized
    public static void setIsSync(boolean isSync) {
        LocalBlockThread.isSync = isSync;
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

            // save new utxo data
            saveNewUtxos(block.getTransactions());
        }
        // update current hash value
        blockChainDb.saveCurrentHash(currentHash);
    }

    /**
     * Save new utxo datas into database.
     * @param transactions block's transactions
     */
    public void saveNewUtxos(List<Transaction> transactions) {
        // save new utxo data
        if (CollectionUtils.isEmpty(transactions)) {
            return;
        }
        for (Transaction transaction : transactions) {
            if (transaction == null) {
                continue;
            }
            // handle one
            saveNewUtxos(transaction);
        }
    }

    /**
     * Convert a transaction's outputs to utxos and save them if needed.
     * @param transaction
     */
    public void saveNewUtxos(Transaction transaction) {
        List<TxOutput> txOutputs = transaction.getTxOutputs();
        if (CollectionUtils.isEmpty(txOutputs)) {
            return;
        }
        Set<String> addressSet = blockChainDb.getWalletAddressSet();
        if (CollectionUtils.isEmpty(addressSet)) {
            return;
        }
        TxOutput tempOutput;
        Utxo tempUtxo;
        String tempAddress;
        for (int i = 0; i < txOutputs.size(); i++) {
            tempOutput = txOutputs.get(i);
            if (tempOutput == null || tempOutput.getPubKeyHash() == null) {
                continue;
            }
            tempAddress = AddressUtil.createAddress(tempOutput.getPubKeyHash());
            if (!addressSet.contains(tempAddress)) {
                continue;
            }
            // vout point to a user address
            tempUtxo = new Utxo();
            tempUtxo.setTxId(transaction.getId());
            tempUtxo.setPublicKeyHash(tempOutput.getPubKeyHash());
            tempUtxo.setAmount(new BigInteger(tempOutput.getValue()));
            tempUtxo.setVoutIndex(i);
            // update utxo
            utxoDb.save(tempUtxo);

            // update utxo index
            utxoIndexDb.save(tempAddress, transaction.getId(), i);
        }
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
        List<Transaction> transactions = new ArrayList<>();
        List<Transaction> tempTransactions;
        for (String blockHash : blockHashes) {
            tempTransactions = transactionDb.get(blockHash);
            transactions.addAll(tempTransactions);

            transactionDb.remove(blockHash);
        }
        // remove related values in utxo
        removeInvalidUtxo(transactions);
    }

    /**
     * Remove all invalid utxo data from database.
     * @param transactions transaciton info list
     */
    private void removeInvalidUtxo(List<Transaction> transactions) {
        if (CollectionUtils.isEmpty(transactions)) {
            return;
        }
        Set<String> addressSet = blockChainDb.getWalletAddressSet();
        for (Transaction transaction : transactions) {
            if (transaction == null) {
                continue;
            }
            // remove stored utxo database's datas
            removeInvalidUtxo(transaction);
            // remove stored utxo index infos (related to user)
            removeInvalidUtxoIndex(transaction, addressSet);
        }
    }

    /**
     * Remove all invalid utxo data from utxo database.
     * @param transaction
     */
    private void removeInvalidUtxo(Transaction transaction) {
        List<TxOutput> txOutputs = transaction.getTxOutputs();
        if (CollectionUtils.isEmpty(txOutputs)) {
            return;
        }
        for (int i = 0; i < txOutputs.size(); i++) {
            utxoDb.remove(transaction.getId(), i);
        }
    }

    /**
     * Remove all invalid utxo index data from utxo index database.
     * @param transaction
     * @param addressetSet user's address set
     */
    private void removeInvalidUtxoIndex(Transaction transaction, Set<String> addressetSet) {
        if (CollectionUtils.isEmpty(addressetSet)) {
            return;
        }
        List<TxOutput> txOutputs = transaction.getTxOutputs();
        if (CollectionUtils.isEmpty(txOutputs)) {
            return;
        }
        for (String tempAddress : addressetSet) {
            if (StringUtils.isEmpty(tempAddress)) {
                continue;
            }
            for (int i = 0; i < txOutputs.size(); i++) {
                utxoIndexDb.remove(tempAddress, transaction.getId(), i);
            }
        }
    }
}
