package com.dappley.java.core.chain;

import com.dappley.java.core.crypto.ShaDigest;
import com.dappley.java.core.po.Transaction;
import com.dappley.java.core.po.TxInput;
import com.dappley.java.core.po.TxOutput;
import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.util.*;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utils to handle transaction datas.
 */
public class TransactionManager {

    /**
     * Create a new transaction
     * @param utxos      user's unspend vouts
     * @param toAddress  account address to be transfered
     * @param amount     alue to be transfered
     * @param privateKey
     * @param tip        transaction tip
     * @param gasLimit   max gas consumption
     * @param gasPrice   gas price
     * @param contract   contract content
     * @return Transaction a new transaction object
     */
    public static Transaction newTransaction(List<Utxo> utxos, String toAddress, BigInteger amount, BigInteger privateKey, BigInteger tip, BigInteger gasLimit, BigInteger gasPrice, String contract) {
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        return newTransaction(utxos, toAddress, amount, keyPair, tip, gasLimit, gasPrice, contract);
    }

    /**
     * Create a new transaction
     * @param utxos     user's unspend vouts
     * @param toAddress account address to be transfered
     * @param amount    value to be transfered
     * @param ecKeyPair user's keypair
     * @param tip       transaction tip
     * @param gasLimit  max gas consumption
     * @param gasPrice  gas price
     * @param contract  contract content
     * @return Transaction a new transaction object
     */
    public static Transaction newTransaction(List<Utxo> utxos, String toAddress, BigInteger amount, ECKeyPair ecKeyPair, BigInteger tip, BigInteger gasLimit, BigInteger gasPrice, String contract) {
        Transaction transaction = new Transaction();
        // add vin list and return the total amount of vin values
        BigInteger totalAmount = buildVin(transaction, utxos, ecKeyPair);

        BigInteger change = calculateChange(totalAmount, amount, tip, gasLimit, gasPrice);

        // add vout list. If there is change is this transaction, vout list wound have two elements, or have just one to coin receiver.
        buildVout(transaction, toAddress, amount, change, ecKeyPair, contract);

        // add default tip value
        transaction.setTip(tip);

        // gas limit and price
        transaction.setGasLimit(gasLimit);
        transaction.setGasPrice(gasPrice);

        if (contract != null && contract.length() > 0) {
            transaction.setType(Transaction.TxTypeContract);
        } else {
            transaction.setType(Transaction.TxTypeNormal);
        }

        // generate Id
        transaction.createId();

        // sign transaction input list
        sign(transaction, ecKeyPair.getPrivateKey(), utxos);

        return transaction;
    }

    /**
     * Build Transaction Vin list
     * @param transaction
     * @param utxos       unspend vouts
     * @param ecKeyPair   user's keypair
     * @return BigInteger total amount can be used
     */
    private static BigInteger buildVin(Transaction transaction, List<Utxo> utxos, ECKeyPair ecKeyPair) {
        TxInput txInput;
        byte[] pubKey = HashUtil.getPubKeyBytes(ecKeyPair.getPublicKey());
        // save total amount value of all txInput value
        BigInteger totalAmount = BigInteger.ZERO;
        for (Utxo utxo : utxos) {
            if (utxo == null) {
                continue;
            }
            txInput = new TxInput();
            txInput.setTxId(utxo.getTxId());
            txInput.setVout(utxo.getVoutIndex());
            // add from publicKey value
            txInput.setPublicKey(pubKey);
            totalAmount = totalAmount.add(utxo.getAmount());
            transaction.addTxInput(txInput);
        }
        return totalAmount;
    }

    /**
     * Build Transaction Vout list
     * @param transaction
     * @param toAddress   To User address
     * @param amount      transfer account
     * @param change      total change, give back to from address
     * @param contract    contract content
     * @param ecKeyPair   user's keypair
     */
    private static void buildVout(Transaction transaction, String toAddress, BigInteger amount, BigInteger change, ECKeyPair ecKeyPair, String contract) {
        if (change != null && change.compareTo(BigInteger.ZERO) < 0) {
            return;
        }
        TxOutput txOutput = new TxOutput();
        if (ObjectUtils.isNotEmpty(contract)) {
            if (ObjectUtils.isEmpty(toAddress)) {
                toAddress = AddressUtil.createContractAddress();
            }
            // set contract output
            txOutput.setContract(contract);
            txOutput.setPublicKeyHash(HashUtil.getPublicKeyHash(toAddress));
            txOutput.setValue(ByteUtil.bigInteger2Bytes(BigInteger.ZERO));
            transaction.addTxOutput(txOutput);
        }
        // send to toAddress
        txOutput = new TxOutput();
        txOutput.setPublicKeyHash(HashUtil.getPublicKeyHash(toAddress));
        txOutput.setValue(ByteUtil.bigInteger2Bytes(amount));
        transaction.addTxOutput(txOutput);

        // if change is greater than 0, we need to give change to from address
        if (change != null && change.compareTo(BigInteger.ZERO) > 0) {
            txOutput = new TxOutput();
            // set from address's pubKeyHash
            byte[] myPubKeyHash = HashUtil.getUserPubKeyHash(ecKeyPair.getPublicKey());
            txOutput.setPublicKeyHash(myPubKeyHash);
            txOutput.setValue(ByteUtil.bigInteger2Bytes(change));
            transaction.addTxOutput(txOutput);
        }
    }

    /**
     * Returns balance change of total transaction consumption
     * @param total    total balance of certain utxos
     * @param amount   transfer amount
     * @param tip      transaction tip
     * @param gasLimit contract tx gas limit
     * @param gasPrice contract tx gas price
     * @return BigInteger changed amount
     */
    private static BigInteger calculateChange(BigInteger total, BigInteger amount, BigInteger tip, BigInteger gasLimit, BigInteger gasPrice) {
        BigInteger change = total.subtract(amount);
        if (change.compareTo(BigInteger.ZERO) < 0) {
            return null;
        }
        change = change.subtract(tip);
        if (change.compareTo(BigInteger.ZERO) < 0) {
            return null;
        }
        if (gasLimit != null && gasPrice != null) {
            change = change.subtract(gasLimit.multiply(gasPrice));
            if (change.compareTo(BigInteger.ZERO) < 0) {
                return null;
            }
        }
        return change;
    }

    /**
     * Prepared to query transaction data by txId. Not implement yet.
     * @param id transaction id
     * @return Transaction
     */
    public static Transaction getTransactionById(byte[] id) {
        // implement find transaction
        return null;
    }

    /**
     * Returns a Map relative to current transaction input list.
     * <p>Each input related to a previous transaction's output.
     * Make sure each txId reference in input is refer to a legal transaction's id.</p>
     * @param txInputs current transaction inputs
     * @return Map related previous transaction data
     */
    public static Map<String, Transaction> getPrevTransactions(List<TxInput> txInputs) {
        Map<String, Transaction> transactionMap = new HashMap<>(txInputs.size());
        for (TxInput txInput : txInputs) {
            Transaction transaction = getTransactionById(txInput.getTxId());
            if (transaction == null) {
                continue;
            }
            transactionMap.put(HexUtil.toHex(txInput.getTxId()), transaction);
        }
        return transactionMap;
    }

    /**
     * Format previous transaction datas
     * @param utxos previous transaction vouts
     * @return Map <txId-voutIndex,utxo>
     */
    public static Map<String, Utxo> getPrevUtxos(List<Utxo> utxos) {
        Map<String, Utxo> utxoMap = new HashMap<>(utxos.size());
        for (Utxo utxo : utxos) {
            utxoMap.put(HexUtil.toHex(utxo.getTxId()) + "-" + utxo.getVoutIndex(), utxo);
        }
        return utxoMap;
    }

    /**
     * Sign for transaction's inputs
     * @param transaction
     * @param privateKey  user's private key
     * @param utxos       user's unspend vouts
     */
    public static void sign(Transaction transaction, BigInteger privateKey, List<Utxo> utxos) {
        List<TxInput> txInputs = transaction.getTxInputs();
        if (ObjectUtils.isEmpty(txInputs)) {
            return;
        }
        // format previous transaction data
        Map<String, Utxo> utxoMap = getPrevUtxos(utxos);

        // no need to validate inputs

        // get a trimedCopy of old transaction
        Transaction transactionCopy = transaction.trimedCopy();

        byte[] privKeyBytes = HashUtil.fromECDSAPrivateKey(privateKey);

        // calculate sign value
        buildSignValue(transaction, utxoMap, transactionCopy, privKeyBytes);
    }

    /**
     * calculate transaction input list's sign value
     * @param transaction     origin transaction
     * @param utxoMap         previous utxos
     * @param transactionCopy copied transaction (for assemble sign value)
     * @param privKeyBytes    user's private key bytes
     */
    private static void buildSignValue(Transaction transaction, Map<String, Utxo> utxoMap, Transaction transactionCopy, byte[] privKeyBytes) {
        List<TxInput> txCopyInputs = transactionCopy.getTxInputs();
        TxInput txCopyInput = null;
        byte[] oldPubKey = null;
        for (int i = 0; i < txCopyInputs.size(); i++) {
            txCopyInput = txCopyInputs.get(i);
            oldPubKey = txCopyInput.getPublicKey();
            Utxo utxo = utxoMap.get(HexUtil.toHex(txCopyInput.getTxId()) + "-" + txCopyInput.getVout());
            // temporarily add pubKeyHash to pubKey property
            txCopyInput.setPublicKey(utxo.getPublicKeyHash());

            // get deepClone's hash value
            byte[] txCopyHash = transactionCopy.hash();

            // recover old pubKey
            txCopyInput.setPublicKey(oldPubKey);

            byte[] signature = HashUtil.secp256k1Sign(txCopyHash, privKeyBytes);

            // Update original transaction data with vin's signature.
            transaction.getTxInputs().get(i).setSignature(signature);
        }
    }

    /**
     * Validate all input refer to a previous transaction's vout
     * @param txInputs       transaction input list
     * @param transactionMap previous transaction data map
     */
    private static void validateTransactionInputs(List<TxInput> txInputs, Map<String, Transaction> transactionMap) {
        for (TxInput txInput : txInputs) {
            if (txInput == null) {
                continue;
            }
            Transaction prev = transactionMap.get(HexUtil.toHex(txInput.getTxId()));
            // previous transaction not found
            if (prev == null) {
                throw new IllegalArgumentException("Previous transaction is invalid");
            }
            List<TxOutput> prevVoutList = prev.getTxOutputs();
            if (ObjectUtils.isEmpty(prevVoutList)) {
                throw new IllegalArgumentException("Previous transaction is invalid");
            }
            // vout index should not be greater than previous vout list size
            if (txInput.getVout() < 0 || txInput.getVout() >= prevVoutList.size()) {
                throw new IllegalArgumentException("Input of the transaction not found in previous transactions");
            }
        }
    }

    /**
     * Returns the hash value of transaction list
     * <p>Combine all hash of each transaction and calculate a SHA256 digest for result.</p>
     * @param transactions transaction list of block
     * @return byte[] hash value
     */
    public static byte[] hashTransactions(List<Transaction> transactions) {
        byte[] txHash = new byte[0];
        for (Transaction transaction : transactions) {
            txHash = ByteUtil.concat(txHash, transaction.hash());
        }
        txHash = ShaDigest.sha256(txHash);
        return txHash;
    }

}
