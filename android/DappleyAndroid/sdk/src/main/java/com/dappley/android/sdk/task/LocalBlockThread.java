package com.dappley.android.sdk.task;

import android.content.Context;
import android.util.Log;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.android.sdk.config.Configuration;
import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.db.BlockIndexDb;
import com.dappley.android.sdk.db.TransactionDb;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.db.UtxoIndexDb;
import com.dappley.java.core.net.DataProvider;
import com.dappley.java.core.net.RemoteDataProvider;
import com.dappley.java.core.po.Block;
import com.dappley.java.core.po.Transaction;
import com.dappley.java.core.po.TxInput;
import com.dappley.java.core.po.TxOutput;
import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.util.AddressUtil;
import com.dappley.java.core.util.HexUtil;
import com.dappley.java.core.util.ObjectUtils;

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
    private String genesisHash;

    public LocalBlockThread(Context context) {
        this.context = context;
        String serverIp = Configuration.getInstance(context).getServerIp();
        int serverPort = Configuration.getInstance(context).getServerPort();

        blockDb = new BlockDb(context);
        blockIndexDb = new BlockIndexDb(context);
        blockChainDb = new BlockChainDb(context);
        transactionDb = new TransactionDb(context);
        utxoDb = new UtxoDb(context);
        utxoIndexDb = new UtxoIndexDb(context);
        dataProvider = new RemoteDataProvider(RemoteDataProvider.RemoteProtocalType.RPC, serverIp, serverPort);

        genesisHash = BlockChainManager.getGenesisHash(context);

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
        if (ObjectUtils.isEmpty(startHashes)) {
            return;
        }
        try {
            // set sysn tag true
            setIsSync(true);

            List<Block> blocks = dataProvider.getBlocks(startHashes, ONCE_COUNT);
            // save blocks to db
            saveSynchronizedBlocks(blocks, startHashes);

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
    private static boolean isIsSync() {
        return isSync;
    }

    /**
     * Set synchronize tag.
     * @param isSync true/false
     */
    @Synchronized
    private static void setIsSync(boolean isSync) {
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
        String currentHash = blockChainDb.getCurrentHash();
        if (ObjectUtils.isEmpty(currentHash)) {
            return null;
        }
        List<String> startHashes = new ArrayList<>();
        startHashes.add(currentHash);
        Block block = blockDb.get(currentHash);
        if (block == null) {
            return startHashes;
        }
        // traverse PREV_COUNT block data
        for (int i = 1; i < PREV_COUNT; i++) {
            if (block.getHeader().getPrevHash() == null
                    || ObjectUtils.isEmpty(HexUtil.toHex(block.getHeader().getPrevHash()))) {
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
     * Save synchronized block datas into db.
     * <p>Save each block data into BlockDb.
     *    RPC protocal blocks is ranged in increase mode. That means the last one in block list is the tail block.
     *    Save transactions data into TransactionDb while BlockDb do not contains transactions info.
     *    Update current hash value in BlockChainDb for next use.
     * </p>
     * <p>
     *     If the first synchronized block is linked to genesis block, the whole blockchain should be replaced by new blocks.
     *     This may happen in some unpredictable scene.
     * </p>
     * @param blocks new tail blocks
     * @param startHashs old tail blocks
     */
    private void saveSynchronizedBlocks(List<Block> blocks, List<String> startHashs) {
        if (ObjectUtils.isEmpty(blocks)) {
            return;
        }
        Log.i(TAG, "saveSynchronizedBlocks count " + blocks.size());

        // handler fork, remove invalid blocks
        String newParentHash = HexUtil.toHex(blocks.get(0).getHeader().getPrevHash());
        if (genesisHash.equals(newParentHash)) {
            // whole chain has been replaced
            clearChain();
        } else {
            // judge if there are some forked blocks
            removeForkedBlocks(newParentHash, startHashs);
        }

        // save new datas
        String currentHash = null;
        for (Block block : blocks) {
            currentHash = HexUtil.toHex(block.getHeader().getHash());
            blockDb.save(block);
            blockIndexDb.save(HexUtil.toHex(block.getHeader().getPrevHash()), currentHash);
            transactionDb.save(currentHash, block.getTransactions());

            // save new utxo data
            saveUnspentVouts(block.getTransactions());
        }
        // update current hash value
        blockChainDb.saveCurrentHash(currentHash);
    }

    /**
     * Clear all datas in database.
     * <p>BlockChainDb has saved wallet addresses, and they should not be cleared.</p>
     */
    private void clearChain() {
        utxoIndexDb.clearAll();
        utxoDb.clearAll();
        transactionDb.clearAll();
        blockIndexDb.clearAll();
        blockDb.clearAll();

        // add genesis block
        BlockChainManager.initGenesisBlock(this.context);
    }

    /**
     * Save new utxo datas into database.
     * @param transactions block's transactions
     */
    private void saveUnspentVouts(List<Transaction> transactions) {
        // save new utxo data
        if (ObjectUtils.isEmpty(transactions)) {
            return;
        }
        for (Transaction transaction : transactions) {
            if (transaction == null) {
                continue;
            }
            // remove spent
            removeSpentVouts(transaction);
            // save unspent
            saveUnspentVouts(transaction);
        }
    }

    /**
     * Remove spent vout in utxoDb and utxoIndexDb
     * @param transaction
     */
    private void removeSpentVouts(Transaction transaction) {
        List<TxInput> txInputs = transaction.getTxInputs();
        if (ObjectUtils.isEmpty(txInputs)) {
            return;
        }
        if (transaction.isCoinbase()) {
            // coinbase transaction do not have related spent vout info
            return;
        }
        Set<String> addressSet = blockChainDb.getWalletAddressSet();
        for (TxInput txInput : txInputs) {
            // remove spent utxo in utxoDb
            utxoDb.remove(txInput.getTxId(), txInput.getVout());
            if (ObjectUtils.isEmpty(addressSet)) {
                continue;
            }
            // remove spent utxo in utxoIndexDb
            for (String address : addressSet) {
                utxoIndexDb.remove(address, txInput.getTxId(), txInput.getVout());
            }
        }
    }

    /**
     * Convert a transaction's outputs to utxos and save them if needed.
     * @param transaction
     */
    private void saveUnspentVouts(Transaction transaction) {
        List<TxOutput> txOutputs = transaction.getTxOutputs();
        if (ObjectUtils.isEmpty(txOutputs)) {
            return;
        }
        Set<String> addressSet = blockChainDb.getWalletAddressSet();
        if (ObjectUtils.isEmpty(addressSet)) {
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
            tempAddress = AddressUtil.getAddressFromPubKeyHash(tempOutput.getPubKeyHash());
            if (!addressSet.contains(tempAddress)) {
                continue;
            }
            // vout point to a user address
            tempUtxo = new Utxo();
            tempUtxo.setTxId(transaction.getId());
            tempUtxo.setPublicKeyHash(tempOutput.getPubKeyHash());
            tempUtxo.setAmount(new BigInteger(1, tempOutput.getValue()));
            tempUtxo.setVoutIndex(i);
            // update utxo
            utxoDb.save(tempUtxo);

            // update utxo index
            utxoIndexDb.save(tempAddress, transaction.getId(), i);
        }
    }

    /**
     * Remove invalid blocks(forked chain) from db
     * <p>Remove some invalid blocks when synchronized blocks from chain are not the same.</p>
     * <p>When the first hash equals to an old one, the process is stopped.</p>
     * @param newParentHash  the first block's hash of synchronized blocks from chain
     * @param oldHashes the last several block hash of chain tail in local db
     */
    private void removeForkedBlocks(String newParentHash, List<String> oldHashes) {
        if (ObjectUtils.isEmpty(newParentHash) || ObjectUtils.isEmpty(oldHashes)) {
            return;
        }
        // form forked block hash list
        List<String> forkedHashes = new ArrayList<>();
        for (String oldHash : oldHashes) {
            if (oldHash.equals(newParentHash)) {
                break;
            }
            // remove from db
            blockDb.remove(oldHash);
            blockIndexDb.remove(oldHash);
            forkedHashes.add(oldHash);
        }

        // remove transactions by forked hashes
        removeForkedTransactions(forkedHashes);
    }

    /**
     * Remove all forked transactions from database.
     * @param blockHashes forked block hash list
     */
    private void removeForkedTransactions(List<String> blockHashes) {
        if (ObjectUtils.isEmpty(blockHashes)) {
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
        removeForkedUtxo(transactions);
    }

    /**
     * Remove all forked utxo data (invalid utxo) from database.
     * @param transactions transaciton info list
     */
    private void removeForkedUtxo(List<Transaction> transactions) {
        if (ObjectUtils.isEmpty(transactions)) {
            return;
        }
        Set<String> addressSet = blockChainDb.getWalletAddressSet();
        for (Transaction transaction : transactions) {
            if (transaction == null) {
                continue;
            }
            // remove stored utxo database's datas
            removeForkedUtxo(transaction);
            // remove stored utxo index infos (related to user)
            removeForkedUtxoIndex(transaction, addressSet);
        }
    }

    /**
     * Remove all forked utxo data from utxo database.
     * @param transaction
     */
    private void removeForkedUtxo(Transaction transaction) {
        List<TxOutput> txOutputs = transaction.getTxOutputs();
        if (ObjectUtils.isEmpty(txOutputs)) {
            return;
        }
        for (int i = 0; i < txOutputs.size(); i++) {
            utxoDb.remove(transaction.getId(), i);
        }
    }

    /**
     * Remove all forked utxo index data from utxo index database.
     * @param transaction
     * @param addressetSet user's address set
     */
    private void removeForkedUtxoIndex(Transaction transaction, Set<String> addressetSet) {
        if (ObjectUtils.isEmpty(addressetSet)) {
            return;
        }
        List<TxOutput> txOutputs = transaction.getTxOutputs();
        if (ObjectUtils.isEmpty(txOutputs)) {
            return;
        }
        for (String tempAddress : addressetSet) {
            if (ObjectUtils.isEmpty(tempAddress)) {
                continue;
            }
            for (int i = 0; i < txOutputs.size(); i++) {
                utxoIndexDb.remove(tempAddress, transaction.getId(), i);
            }
        }
    }
}
