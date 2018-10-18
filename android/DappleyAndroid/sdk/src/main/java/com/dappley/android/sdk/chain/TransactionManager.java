package com.dappley.android.sdk.chain;

import com.dappley.android.sdk.crypto.ShaDigest;
import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.protobuf.TransactionProto;
import com.dappley.android.sdk.util.ByteUtil;
import com.dappley.android.sdk.util.HashUtil;
import com.dappley.android.sdk.util.HexUtil;
import com.google.protobuf.ByteString;

import org.apache.commons.collections4.CollectionUtils;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utils to handle transaction datas.
 */
public class TransactionManager {
    // default tip of each transaction
    private static final long TIP_DEFAULT = 0;

    public static TransactionProto.Transaction newTransaction(String fromAddress, String toAddress, int amount, ECKeyPair ecKeyPair) {
        List<RpcProto.UTXO> spendableList = UtxoManager.getSpendableUtxos(fromAddress, amount);
        return newTransaction(spendableList, toAddress, amount, ecKeyPair);
    }

    public static TransactionProto.Transaction newTransaction(List<RpcProto.UTXO> utxos, String toAddress, int amount, ECKeyPair ecKeyPair) {
        TransactionProto.Transaction.Builder builder = TransactionProto.Transaction.newBuilder();
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

    private static int buildVin(TransactionProto.Transaction.Builder builder, List<RpcProto.UTXO> utxos, ECKeyPair ecKeyPair) {
        TransactionProto.TXInput.Builder txInputBuilder = null;
        // save total amount value of all txInput value
        int totalAmount = 0;
        for (RpcProto.UTXO utxo : utxos) {
            if (utxo == null) {
                continue;
            }
            txInputBuilder = TransactionProto.TXInput.newBuilder();
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

    private static void buildVout(TransactionProto.Transaction.Builder builder, String toAddress, int amount, int totalAmount, ECKeyPair ecKeyPair) {
        if (totalAmount < amount) {
            return;
        }
        TransactionProto.TXOutput.Builder txOutputBuilder = TransactionProto.TXOutput.newBuilder();
        // set to address's pubKeyHash
        txOutputBuilder.setPubKeyHash(ByteString.copyFrom(HashUtil.getPubKeyHash(toAddress)));
        // TODO confirm intTo byte[] is right
        txOutputBuilder.setValue(ByteString.copyFrom(ByteUtil.fromInt(amount)));
        builder.addVout(txOutputBuilder.build());

        // if totalAmout is greater than amount, we need to add change value after.
        if (totalAmount > amount) {
            txOutputBuilder = TransactionProto.TXOutput.newBuilder();
            // set from address's pubKeyHash
            byte[] myPubKeyHash = HashUtil.getPubKeyHash(ecKeyPair.getPublicKey());
            txOutputBuilder.setPubKeyHash(ByteString.copyFrom(myPubKeyHash));
            // TODO confirm intTo byte[] is right
            txOutputBuilder.setValue(ByteString.copyFrom(ByteUtil.fromInt(amount)));
            builder.addVout(txOutputBuilder.build());
        }
    }

    /**
     * Generate ID of Transaction object
     * <p>The ID is a ByteString object</p>
     * @param transactionBuilder builder
     * @return ByteString ID bytes
     */
    public static ByteString newId(TransactionProto.Transaction.Builder transactionBuilder) {
        transactionBuilder.setID(ByteString.copyFrom(new byte[]{}));
        TransactionProto.Transaction transaction = transactionBuilder.build();
        // serialize transaction object
        byte[] txBytes = serialize(transaction);
        byte[] sha256 = ShaDigest.sha256(txBytes);
        return ByteString.copyFrom(sha256);
    }

    /**
     * Returns the hash value of transaction
     * @param transaction a transaction in transaction list
     * @return byte[] hash value
     */
    public static byte[] hash(TransactionProto.Transaction transaction) {
        TransactionProto.Transaction.Builder txBuilder = transaction.toBuilder();
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
    public static byte[] hashTransactions(List<TransactionProto.Transaction> transactions) {
        byte[] txHash = new byte[0];
        for (TransactionProto.Transaction transaction : transactions) {
            txHash = ByteUtil.concat(txHash, hash(transaction));
        }
        txHash = ShaDigest.sha256(txHash);
        return txHash;
    }

    public static byte[] serialize(TransactionProto.Transaction transaction) {
        // TODO serialize transaction

        return new byte[]{};
    }

    public static TransactionProto.Transaction getTransactionById(ByteString id) {
        // TODO implement find transaction

        return null;
    }

    /**
     * Returns a Map relative to current transaction input list.
     * <p>Each input related to a previous transaction's output.
     *    Make sure each txId reference in input is refer to a legal transaction's id.</p>
     * @param txInputs current transaction inputs
     * @return Map<String ,   TransactionProto.Transaction> related previous transaction data
     */
    public static Map<String, TransactionProto.Transaction> getPrevTransactions(List<TransactionProto.TXInput> txInputs) {
        Map<String, TransactionProto.Transaction> transactionMap = new HashMap<>(txInputs.size());
        for (TransactionProto.TXInput txInput : txInputs) {
            TransactionProto.Transaction transaction = getTransactionById(txInput.getTxid());
            if (transaction == null) {
                continue;
            }
            transactionMap.put(HexUtil.toHex(txInput.getTxid()), transaction);
        }
        return transactionMap;
    }

    public static void sign(TransactionProto.Transaction.Builder txBuilder, BigInteger privateKey) {
        List<TransactionProto.TXInput> txInputs = txBuilder.getVinList();
        if (CollectionUtils.isEmpty(txInputs)) {
            return;
        }
        // format previous transaction data
        Map<String, TransactionProto.Transaction> transactionMap = getPrevTransactions(txInputs);

        // validate all inputs is legal
        validateTransactionInputs(txInputs, transactionMap);

        // get a copy of old transaction builder
        TransactionProto.Transaction.Builder txCopyBuilder = trimedCopy(txBuilder);

        byte[] privKeyHash = HashUtil.fromECDSAPrivateKey(privateKey);

        // calculate sign value
        buildSignValue(txBuilder, transactionMap, txCopyBuilder, privKeyHash);
    }

    private static void buildSignValue(TransactionProto.Transaction.Builder txBuilder, Map<String, TransactionProto.Transaction> transactionMap, TransactionProto.Transaction.Builder txCopyBuilder, byte[] privKeyHash) {
        List<TransactionProto.TXInput> txCopyInputs = txCopyBuilder.getVinList();
        TransactionProto.TXInput.Builder tmpTxBuilder = null;
        TransactionProto.TXInput txCopyInput = null;
        for (int i = 0; i < txCopyInputs.size(); i++) {
            txCopyInput = txCopyInputs.get(i);
            tmpTxBuilder = txCopyInput.toBuilder();
            TransactionProto.Transaction prev = transactionMap.get(HexUtil.toHex(txCopyInput.getTxid()));
            tmpTxBuilder.setSignature(null);
            // temporarily add pubKeyHash to pubKey property
            tmpTxBuilder.setPubKey(prev.getVout(txCopyInput.getVout()).getPubKeyHash());
            // hash to get a new ID
            tmpTxBuilder.setTxid(newId(txCopyBuilder));
            tmpTxBuilder.setPubKey(null);

            byte[] signature = HashUtil.secp256k1Sign(tmpTxBuilder.getTxid().toByteArray(), privKeyHash);

            // fill signature to each original transaction input
            updateInputSignature(txBuilder, i, signature);
        }
    }

    private static void updateInputSignature(TransactionProto.Transaction.Builder txBuilder, int index, byte[] signature) {
        TransactionProto.TXInput.Builder txInputBuilder = txBuilder.getVin(index).toBuilder()
                .setSignature(ByteString.copyFrom(signature));
        txBuilder.setVin(index, txInputBuilder);
    }

    private static void validateTransactionInputs(List<TransactionProto.TXInput> txInputs, Map<String, TransactionProto.Transaction> transactionMap) {
        for (TransactionProto.TXInput txInput : txInputs) {
            if (txInput == null) {
                continue;
            }
            TransactionProto.Transaction prev = transactionMap.get(HexUtil.toHex(txInput.getTxid()));
            // previous transaction not found
            if (prev == null) {
                throw new IllegalArgumentException("Previous transaction is invalid");
            }
            List<TransactionProto.TXOutput> prevVoutList = prev.getVoutList();
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
    private static TransactionProto.Transaction.Builder trimedCopy(TransactionProto.Transaction.Builder txBuilder) {
        TransactionProto.Transaction.Builder newBuilder = txBuilder.clone();
        List<TransactionProto.TXInput> txInputs = newBuilder.getVinList();
        if (CollectionUtils.isEmpty(txInputs)) {
            return null;
        }
        List<TransactionProto.TXInput> txNewInputs = new ArrayList<>(txInputs.size());
        TransactionProto.TXInput.Builder tmpTxBuilder = null;
        for (TransactionProto.TXInput txInput : txInputs) {
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
