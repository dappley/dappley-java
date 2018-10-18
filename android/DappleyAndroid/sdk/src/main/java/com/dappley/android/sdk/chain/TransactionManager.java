package com.dappley.android.sdk.chain;

import com.dappley.android.sdk.crypto.ShaDigest;
import com.dappley.android.sdk.protobuf.RpcProto.UTXO;
import com.dappley.android.sdk.protobuf.TransactionProto.TXInput;
import com.dappley.android.sdk.protobuf.TransactionProto.TXOutput;
import com.dappley.android.sdk.protobuf.TransactionProto.Transaction;
import com.dappley.android.sdk.util.ByteUtil;
import com.dappley.android.sdk.util.HashUtil;
import com.dappley.android.sdk.util.HexUtil;
import com.google.protobuf.ByteString;

import org.apache.commons.collections4.CollectionUtils;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Utils to handle transaction datas.
 */
public class TransactionManager {
    // default tip of each transaction
    private static final long TIP_DEFAULT = 0;

    public static Transaction newTransaction(String fromAddress, String toAddress, int amount, ECKeyPair ecKeyPair) {
        List<UTXO> spendableList = UtxoManager.getSpendableUtxos(fromAddress, amount);
        return newTransaction(spendableList, toAddress, amount, ecKeyPair);
    }

    public static Transaction newTransaction(List<UTXO> utxos, String toAddress, int amount, ECKeyPair ecKeyPair) {
        Transaction.Builder builder = Transaction.newBuilder();
        // add vin list and return the total amount of vin values
        int totalAmount = buildVin(builder, utxos, ecKeyPair);

        // add vout list. If there is change is this transaction, vout list wound have two elements, or have just one to coin receiver.
        buildVout(builder, toAddress, amount, totalAmount, ecKeyPair);

        // add default tip value
        builder.setTip(TIP_DEFAULT);

        // generate Id
        builder.setID(newId(builder));

        // sign transaction input list
        sign(builder, ecKeyPair.getPrivateKey());

        return builder.build();
    }

    private static int buildVin(Transaction.Builder builder, List<UTXO> utxos, ECKeyPair ecKeyPair) {
        TXInput.Builder txInputBuilder;
        // save total amount value of all txInput value
        int totalAmount = 0;
        for (UTXO utxo : utxos) {
            if (utxo == null) {
                continue;
            }
            txInputBuilder = TXInput.newBuilder();
            txInputBuilder.setTxid(utxo.getTxid());
            txInputBuilder.setVout(utxo.getTxIndex());
            // add from publicKey value
            byte[] pubKey = ecKeyPair.getPublicKey().toByteArray();
            txInputBuilder.setPubKey(ByteString.copyFrom(pubKey));
            totalAmount += utxo.getAmount();
            builder.addVin(txInputBuilder.build());
        }
        return totalAmount;
    }

    private static void buildVout(Transaction.Builder builder, String toAddress, int amount, int totalAmount, ECKeyPair ecKeyPair) {
        if (totalAmount < amount) {
            return;
        }
        TXOutput.Builder txOutputBuilder = TXOutput.newBuilder();
        // set to address's pubKeyHash
        txOutputBuilder.setPubKeyHash(ByteString.copyFrom(HashUtil.getPubKeyHash(toAddress)));
        txOutputBuilder.setValue(ByteString.copyFrom(ByteUtil.int2Bytes(amount)));
        builder.addVout(txOutputBuilder.build());

        // if totalAmout is greater than amount, we need to add change value after.
        if (totalAmount > amount) {
            txOutputBuilder = TXOutput.newBuilder();
            // set from address's pubKeyHash
            byte[] myPubKeyHash = HashUtil.getPubKeyHash(ecKeyPair.getPublicKey());
            txOutputBuilder.setPubKeyHash(ByteString.copyFrom(myPubKeyHash));
            txOutputBuilder.setValue(ByteString.copyFrom(ByteUtil.int2Bytes(amount)));
            builder.addVout(txOutputBuilder.build());
        }
    }

    /**
     * Generate ID of Transaction object
     * <p>The ID is a ByteString object</p>
     * @param transactionBuilder builder
     * @return ByteString ID bytes
     */
    public static ByteString newId(Transaction.Builder transactionBuilder) {
        byte[] hash = hashTransaction(transactionBuilder);
        return ByteString.copyFrom(hash);
    }

    /**
     * Returns the hash value of transaction
     * @param transaction
     * @return byte[] hash value
     */
    public static byte[] hashTransaction(Transaction transaction) {
        return hashTransaction(transaction.toBuilder());
    }

    /**
     * Returns the hash value of transaction
     * @param txBuilder a transaction in transaction list
     * @return byte[] hash value
     */
    public static byte[] hashTransaction(Transaction.Builder txBuilder) {
        // clear id property
        txBuilder.setID(ByteUtil.EMPTY_BYTE_STRING);

        // serialize the new transaction oject
        byte[] serialized = serialize(txBuilder.build());
        // get sha256 hash
        byte[] hash = ShaDigest.sha256(serialized);
        return hash;
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
            txHash = ByteUtil.concat(txHash, hashTransaction(transaction));
        }
        txHash = ShaDigest.sha256(txHash);
        return txHash;
    }

    public static byte[] serialize(Transaction transaction) {
        // serialize transaction
        List<byte[]> bytesList = new LinkedList<>();

        // add vin
        List<TXInput> txInputs = transaction.getVinList();
        if (txInputs != null) {
            for (TXInput txInput : txInputs) {
                // txid
                bytesList.add(txInput.getTxid() == null ? null : txInput.getTxid().toByteArray());
                // vout
                bytesList.add(ByteUtil.int2Bytes(txInput.getVout()));
                // pubkey
                bytesList.add(txInput.getPubKey() == null ? null : txInput.getPubKey().toByteArray());
                // signature
                bytesList.add(txInput.getSignature() == null ? null : txInput.getSignature().toByteArray());
            }
        }

        // add vout
        List<TXOutput> txOutputs = transaction.getVoutList();
        if (txOutputs != null) {
            for (TXOutput txOutput : txOutputs) {
                // value
                bytesList.add(txOutput.getValue() == null ? null : txOutput.getValue().toByteArray());
                // pubKeyHash
                bytesList.add(txOutput.getPubKeyHash() == null ? null : txOutput.getPubKeyHash().toByteArray());
            }
        }

        // add tip
        bytesList.add(ByteUtil.long2Bytes(transaction.getTip()));

        return ByteUtil.joinBytes(bytesList);
    }

    public static Transaction getTransactionById(ByteString id) {
        // TODO implement find transaction

        return null;
    }

    /**
     * Returns a Map relative to current transaction input list.
     * <p>Each input related to a previous transaction's output.
     *    Make sure each txId reference in input is refer to a legal transaction's id.</p>
     * @param txInputs current transaction inputs
     * @return Map related previous transaction data
     */
    public static Map<String, Transaction> getPrevTransactions(List<TXInput> txInputs) {
        Map<String, Transaction> transactionMap = new HashMap<>(txInputs.size());
        for (TXInput txInput : txInputs) {
            Transaction transaction = getTransactionById(txInput.getTxid());
            if (transaction == null) {
                continue;
            }
            transactionMap.put(HexUtil.toHex(txInput.getTxid()), transaction);
        }
        return transactionMap;
    }

    public static void sign(Transaction.Builder txBuilder, BigInteger privateKey) {
        List<TXInput> txInputs = txBuilder.getVinList();
        if (CollectionUtils.isEmpty(txInputs)) {
            return;
        }
        // format previous transaction data
        Map<String, Transaction> transactionMap = getPrevTransactions(txInputs);

        // validate all inputs is legal
        validateTransactionInputs(txInputs, transactionMap);

        // get a copy of old transaction builder
        Transaction.Builder txCopyBuilder = trimedCopy(txBuilder);

        byte[] privKeyBytes = HashUtil.fromECDSAPrivateKey(privateKey);

        // calculate sign value
        buildSignValue(txBuilder, transactionMap, txCopyBuilder, privKeyBytes);
    }

    private static void buildSignValue(Transaction.Builder txBuilder, Map<String, Transaction> transactionMap, Transaction.Builder txCopyBuilder, byte[] privKeyBytes) {
        List<TXInput> txCopyInputs = txCopyBuilder.getVinList();
        TXInput.Builder tmpTxBuilder = null;
        TXInput txCopyInput = null;
        for (int i = 0; i < txCopyInputs.size(); i++) {
            txCopyInput = txCopyInputs.get(i);
            tmpTxBuilder = txCopyInput.toBuilder();
            Transaction prev = transactionMap.get(HexUtil.toHex(txCopyInput.getTxid()));
            tmpTxBuilder.setSignature(null);
            // temporarily add pubKeyHash to pubKey property
            tmpTxBuilder.setPubKey(prev.getVout(txCopyInput.getVout()).getPubKeyHash());
            // hash to get a new ID
            tmpTxBuilder.setTxid(newId(txCopyBuilder));
            tmpTxBuilder.setPubKey(null);

            byte[] signature = HashUtil.secp256k1Sign(tmpTxBuilder.getTxid().toByteArray(), privKeyBytes);

            // fill signature to each original transaction input
            updateInputSignature(txBuilder, i, signature);
        }
    }

    private static void updateInputSignature(Transaction.Builder txBuilder, int index, byte[] signature) {
        TXInput.Builder txInputBuilder = txBuilder.getVin(index).toBuilder()
                .setSignature(ByteString.copyFrom(signature));
        txBuilder.setVin(index, txInputBuilder);
    }

    private static void validateTransactionInputs(List<TXInput> txInputs, Map<String, Transaction> transactionMap) {
        for (TXInput txInput : txInputs) {
            if (txInput == null) {
                continue;
            }
            Transaction prev = transactionMap.get(HexUtil.toHex(txInput.getTxid()));
            // previous transaction not found
            if (prev == null) {
                throw new IllegalArgumentException("Previous transaction is invalid");
            }
            List<TXOutput> prevVoutList = prev.getVoutList();
            if (CollectionUtils.isEmpty(prevVoutList)) {
                throw new IllegalArgumentException("Previous transaction is invalid");
            }
            // vout index should not be greater than previous vout list size
            if (txInput.getVout() < 0 || txInput.getVout() >= prevVoutList.size()) {
                throw new IllegalArgumentException("Input of the transaction not found in previous transactions");
            }
        }
    }

    /**
     * trimedCopy creates a trimmed copy of Transaction to be used in signing
     * @param txBuilder old builder
     * @return Transaction.Builder new builder
     */
    private static Transaction.Builder trimedCopy(Transaction.Builder txBuilder) {
        Transaction.Builder newBuilder = txBuilder.clone();
        List<TXInput> txInputs = newBuilder.getVinList();
        if (CollectionUtils.isEmpty(txInputs)) {
            return null;
        }
        List<TXInput> txNewInputs = new ArrayList<>(txInputs.size());
        TXInput.Builder tmpTxBuilder = null;
        for (TXInput txInput : txInputs) {
            tmpTxBuilder = txInput.toBuilder();
            // clear pubKey and signature
            tmpTxBuilder.setPubKey(null);
            tmpTxBuilder.setSignature(null);
            txInput = tmpTxBuilder.build();
            txNewInputs.add(txInput);
        }
        // remove all old inputs
        newBuilder.clearVin();
        // add new input list
        newBuilder.addAllVin(txNewInputs);
        return newBuilder;
    }
}
