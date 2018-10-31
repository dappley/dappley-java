package com.dappley.android.sdk.chain;

import android.content.Context;
import android.util.Log;

import com.dappley.android.sdk.DappleyClient;
import com.dappley.android.sdk.db.BlockChainDb;
import com.dappley.android.sdk.db.BlockIndexDb;
import com.dappley.android.sdk.db.TransactionDb;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.db.UtxoIndexDb;
import com.dappley.android.sdk.po.Transaction;
import com.dappley.android.sdk.po.TxInput;
import com.dappley.android.sdk.po.TxOutput;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.util.AddressUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils to handle UTXO datas.
 */
public class UtxoManager {
    private static final String TAG = "UtxoManager";

    /**
     * Returns suitable utxo list of the address
     * @param address user's account address
     * @param amount target vout amount
     * @return List<Utxo> unspend vouts meet the need for target amount
     */
    public static List<Utxo> getSpendableUtxos(String address, BigInteger amount) {
        try {
            List<Utxo> all = DappleyClient.getUtxo(address);
            return getSpendableUtxos(all, amount);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getSpendableUtxos: ", e);
        }
        return null;
    }

    /**
     * Reform utxo list of target amount
     * @param utxos all user's utxo
     * @param amount target amount
     * @return List<Utxo> suitable utxo list
     */
    public static List<Utxo> getSpendableUtxos(List<Utxo> utxos, BigInteger amount) {
        if (CollectionUtils.isEmpty(utxos)) {
            return null;
        }
        List<Utxo> spendables = new ArrayList<>();
        BigInteger accumulated = BigInteger.ZERO;
        for (Utxo utxo : utxos) {
            if (utxo == null) {
                continue;
            }
            accumulated = accumulated.add(utxo.getAmount());
            spendables.add(utxo);
            if (accumulated.compareTo(amount) >= 0) {
                break;
            }
        }
        if (accumulated.compareTo(amount) < 0) {
            throw new IllegalArgumentException("there is no enough vout");
        }
        return spendables;
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
            if (StringUtils.isEmpty(nextHash)) {
                break;
            }
            // get transactions
            List<Transaction> transactions = transactionDb.get(nextHash);
            if (CollectionUtils.isEmpty(transactions)) {
                continue;
            }
            // compute utxo by transactions
            saveUnspentVouts(transactions, walletAddress, utxoDb, utxoIndexDb);

            hash = nextHash;
        }
    }

    /**
     * Save current wallet address utxo datas into database.
     * @param transactions block's transactions
     * @param walletAddress wallet address
     * @param utxoDb utxo database
     * @param utxoIndexDb utxo index database
     */
    public static void saveUnspentVouts(List<Transaction> transactions, String walletAddress, UtxoDb utxoDb, UtxoIndexDb utxoIndexDb) {
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
    public static void removeSpentVouts(Transaction transaction, String walletAddress, UtxoDb utxoDb, UtxoIndexDb utxoIndexDb) {
        List<TxInput> txInputs = transaction.getTxInputs();
        if (CollectionUtils.isEmpty(txInputs)) {
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
     * Convert buildUserUtxo transaction's outputs to utxos and save them if needed.
     * @param transaction
     * @param walletAddress wallet address
     * @param utxoDb utxo database
     * @param utxoIndexDb utxo index database
     */
    public static void saveUnspentVouts(Transaction transaction, String walletAddress, UtxoDb utxoDb, UtxoIndexDb utxoIndexDb) {
        List<TxOutput> txOutputs = transaction.getTxOutputs();
        if (CollectionUtils.isEmpty(txOutputs)) {
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
            if (!walletAddress.equals(tempAddress)) {
                continue;
            }
            // vout point to buildUserUtxo user address
            tempUtxo = new Utxo();
            tempUtxo.setTxId(transaction.getId());
            tempUtxo.setPublicKeyHash(tempOutput.getPubKeyHash());
            tempUtxo.setAmount(new BigInteger(tempOutput.getValue()));
            tempUtxo.setVoutIndex(i);
            // update utxo
            utxoDb.save(tempUtxo);

            // update utxo index
            utxoIndexDb.save(walletAddress, transaction.getId(), i);
        }
    }
}
