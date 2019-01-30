package com.dappley.android.sdk.chain;

import android.content.Context;

import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockIndexDb;
import com.dappley.android.sdk.db.TransactionDb;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.db.UtxoIndexDb;
import com.dappley.java.core.po.Transaction;
import com.dappley.java.core.po.TxInput;
import com.dappley.java.core.po.TxOutput;
import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.po.UtxoIndex;
import com.dappley.java.core.util.AddressUtil;
import com.dappley.java.core.util.ObjectUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * Utils to handle UTXO datas.
 */
public class UtxoManager {
    private static final String TAG = "UtxoManager";

    /**
     * Reform utxo list of target amount
     * @param utxos all user's utxo
     * @param amount target amount
     * @return List<Utxo> suitable utxo list
     */
    public static List<Utxo> getSuitableUtxos(List<Utxo> utxos, BigInteger amount) {
        return com.dappley.java.core.chain.UtxoManager.getSuitableUtxos(utxos, amount);
    }

    /**
     * Build new user's utxo info.
     * <p>Traverse all blocks from genesis to tail and compute utxo info about current wallet address.</p>
     * @param context
     * @param walletAddress wallet address
     */
    public static void buildUserUtxo(Context context, String walletAddress) {
        BlockChainDb blockChainDb = new BlockChainDb(context);
        BlockIndexDb blockIndexDb = new BlockIndexDb(context);
        TransactionDb transactionDb = new TransactionDb(context);
        UtxoDb utxoDb = new UtxoDb(context);
        UtxoIndexDb utxoIndexDb = new UtxoIndexDb(context);

        String hash = blockChainDb.getGenesisHash();
        String nextHash;
        while (true) {
            nextHash = blockIndexDb.get(hash);
            if (ObjectUtils.isEmpty(nextHash)) {
                break;
            }
            // get transactions
            List<Transaction> transactions = transactionDb.get(nextHash);
            if (ObjectUtils.isEmpty(transactions)) {
                continue;
            }
            // compute utxo by transactions
            saveUnspentVouts(transactions, walletAddress, utxoDb, utxoIndexDb);

            hash = nextHash;
        }
    }

    /**
     * Remove all new user's utxo info.
     * <p>Remove values in UtxoIndex db. Remove key-values pairs in Utxo db.</p>
     * @param context
     * @param walletAddress wallet address
     */
    public static void removeUserUtxo(Context context, String walletAddress) {
        UtxoDb utxoDb = new UtxoDb(context);
        UtxoIndexDb utxoIndexDb = new UtxoIndexDb(context);
        Set<UtxoIndex> utxoIndexSet = utxoIndexDb.get(walletAddress);
        if (ObjectUtils.isEmpty(utxoIndexSet)) {
            return;
        }
        // remove all related keys in utxo db
        for (UtxoIndex utxoIndex : utxoIndexSet) {
            if (utxoIndex == null) {
                continue;
            }
            utxoDb.remove(utxoIndex.toString());
        }
        // remove related values in utxoIndex db
        utxoIndexDb.remove(walletAddress);
    }

    /**
     * Save current wallet address utxo datas into database.
     * @param transactions block's transactions
     * @param walletAddress wallet address
     * @param utxoDb utxo database
     * @param utxoIndexDb utxo index database
     */
    private static void saveUnspentVouts(List<Transaction> transactions, String walletAddress, UtxoDb utxoDb, UtxoIndexDb utxoIndexDb) {
        for (Transaction transaction : transactions) {
            if (transaction == null) {
                continue;
            }
            // remove spent
            removeSpentVouts(transaction, walletAddress, utxoDb, utxoIndexDb);
            // save unspent
            saveUnspentVouts(transaction, walletAddress, utxoDb, utxoIndexDb);
        }
    }

    /**
     * Remove spent vout in utxoDb and utxoIndexDb
     * @param transaction
     * @param utxoDb utxo database
     * @param utxoIndexDb utxo index database
     */
    private static void removeSpentVouts(Transaction transaction, String walletAddress, UtxoDb utxoDb, UtxoIndexDb utxoIndexDb) {
        List<TxInput> txInputs = transaction.getTxInputs();
        if (ObjectUtils.isEmpty(txInputs)) {
            return;
        }
        if (transaction.isCoinbase()) {
            // coinbase transaction do not have related spent vout info
            return;
        }
        for (TxInput txInput : txInputs) {
            // remove spent utxo in utxoDb
            utxoDb.remove(txInput.getTxId(), txInput.getVout());
            // remove spent utxo in utxoIndexDb
            utxoIndexDb.remove(walletAddress, txInput.getTxId(), txInput.getVout());
        }
    }

    /**
     * Convert a transaction's outputs to utxos and save them if needed.
     * @param transaction
     * @param walletAddress wallet address
     * @param utxoDb utxo database
     * @param utxoIndexDb utxo index database
     */
    private static void saveUnspentVouts(Transaction transaction, String walletAddress, UtxoDb utxoDb, UtxoIndexDb utxoIndexDb) {
        List<TxOutput> txOutputs = transaction.getTxOutputs();
        if (ObjectUtils.isEmpty(txOutputs)) {
            return;
        }
        TxOutput tempOutput;
        Utxo tempUtxo;
        String tempAddress;
        for (int i = 0; i < txOutputs.size(); i++) {
            tempOutput = txOutputs.get(i);
            if (tempOutput == null || tempOutput.getPublicKeyHash() == null) {
                continue;
            }
            tempAddress = AddressUtil.getAddressFromPubKeyHash(tempOutput.getPublicKeyHash());
            if (!walletAddress.equals(tempAddress)) {
                continue;
            }
            // vout point to a user address
            tempUtxo = new Utxo();
            tempUtxo.setTxId(transaction.getId());
            tempUtxo.setPublicKeyHash(tempOutput.getPublicKeyHash());
            tempUtxo.setAmount(new BigInteger(1, tempOutput.getValue()));
            tempUtxo.setVoutIndex(i);
            // update utxo
            utxoDb.save(tempUtxo);

            // update utxo index
            utxoIndexDb.save(walletAddress, transaction.getId(), i);
        }
    }
}
