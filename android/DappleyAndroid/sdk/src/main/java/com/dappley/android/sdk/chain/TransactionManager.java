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
    /**
     * default tip of each transaction
     */
    private static final long TIP_DEFAULT = 0;

    /**
     * Create a new transaction
     * @param fromAddress user's account address
     * @param toAddress account address to be transfered
     * @param amount alue to be transfered
     * @param ecKeyPair user's keypair
     * @return Transaction a new transaction object
     */
    public static Transaction newTransaction(String fromAddress, String toAddress, BigInteger amount, ECKeyPair ecKeyPair) {
        List<UTXO> spendableList = UtxoManager.getSpendableUtxos(fromAddress, amount);
        return newTransaction(spendableList, toAddress, amount, ecKeyPair);
    }

    /**
     * Create a new transaction
     * @param utxos user's unspend vouts
     * @param toAddress account address to be transfered
     * @param amount value to be transfered
     * @param ecKeyPair user's keypair
     * @return Transaction a new transaction object
     */
    public static Transaction newTransaction(List<UTXO> utxos, String toAddress, BigInteger amount, ECKeyPair ecKeyPair) {
        Transaction.Builder builder = Transaction.newBuilder();
        // add vin list and return the total amount of vin values
        BigInteger totalAmount = buildVin(builder, utxos, ecKeyPair);

        // add vout list. If there is change is this transaction, vout list wound have two elements, or have just one to coin receiver.
        buildVout(builder, toAddress, amount, totalAmount, ecKeyPair);

        // add default tip value
        builder.setTip(TIP_DEFAULT);

        // generate Id
        builder.setID(newId(builder));

        // sign transaction input list
        sign(builder, ecKeyPair.getPrivateKey(), utxos);

        return builder.build();
    }

    /**
     * Build Transaction Vin list
     * @param builder transaction builder
     * @param utxos unspend vouts
     * @param ecKeyPair user's keypair
     * @return BigInteger total amount can be used
     */
    private static BigInteger buildVin(Transaction.Builder builder, List<UTXO> utxos, ECKeyPair ecKeyPair) {
        TXInput.Builder txInputBuilder;
        byte[] pubKey = ecKeyPair.getPublicKey().toByteArray();
        // save total amount value of all txInput value
        BigInteger totalAmount = BigInteger.ZERO;
        for (UTXO utxo : utxos) {
            if (utxo == null) {
                continue;
            }
            txInputBuilder = TXInput.newBuilder();
            txInputBuilder.setTxid(utxo.getTxid());
            txInputBuilder.setVout(utxo.getTxIndex());
            // add from publicKey value
            txInputBuilder.setPubKey(ByteString.copyFrom(pubKey));
            totalAmount = totalAmount.add(BigInteger.valueOf(utxo.getAmount()));
            builder.addVin(txInputBuilder.build());
        }
        return totalAmount;
    }

    /**
     * Build Transaction Vout list
     * @param builder transaction builder
     * @param toAddress To User address
     * @param amount transfer account
     * @param totalAmount utxo's all vout values
     * @param ecKeyPair user's keypair
     */
    private static void buildVout(Transaction.Builder builder, String toAddress, BigInteger amount, BigInteger totalAmount, ECKeyPair ecKeyPair) {
        if (totalAmount.compareTo(amount) < 0) {
            return;
        }
        TXOutput.Builder txOutputBuilder = TXOutput.newBuilder();
        // set to address's pubKeyHash
        txOutputBuilder.setPubKeyHash(ByteString.copyFrom(HashUtil.getPubKeyHash(toAddress)));
        txOutputBuilder.setValue(ByteString.copyFrom(ByteUtil.bigInteger2Bytes(new BigInteger(String.valueOf(amount)))));
        builder.addVout(txOutputBuilder.build());

        // if totalAmout is greater than amount, we need to add change value after.
        if (totalAmount.compareTo(amount) > 0) {
            txOutputBuilder = TXOutput.newBuilder();
            // set from address's pubKeyHash
            byte[] myPubKeyHash = HashUtil.getPubKeyHash(ecKeyPair.getPublicKey());
            txOutputBuilder.setPubKeyHash(ByteString.copyFrom(myPubKeyHash));
            txOutputBuilder.setValue(ByteString.copyFrom(ByteUtil.bigInteger2Bytes(new BigInteger(String.valueOf(amount)))));
            builder.addVout(txOutputBuilder.build());
        }
    }


    /**
     * Prepared to query transaction data by txId. Not implement yet.
     * @param id transaction id
     * @return Transaction
     */
    public static Transaction getTransactionById(ByteString id) {
        // implement find transaction
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

    /**
     * Format previous transaction datas
     * @param utxos previous transaction vouts
     * @return Map <txId-txIndex,utxo>
     */
    public static Map<String, UTXO> getPrevUtxos(List<UTXO> utxos) {
        Map<String, UTXO> utxoMap = new HashMap<>(utxos.size());
        for (UTXO utxo : utxos) {
            utxoMap.put(HexUtil.toHex(utxo.getTxid()) + "-" + utxo.getTxIndex(), utxo);
        }
        return utxoMap;
    }

    /**
     * Sign for transaction's inputs
     * @param txBuilder transaction builder
     * @param privateKey user's private key
     * @param utxos user's unspend vouts
     */
    public static void sign(Transaction.Builder txBuilder, BigInteger privateKey, List<UTXO> utxos) {
        List<TXInput> txInputs = txBuilder.getVinList();
        if (CollectionUtils.isEmpty(txInputs)) {
            return;
        }
        // format previous transaction data
        Map<String, UTXO> utxoMap = getPrevUtxos(utxos);

        // no need to validate inputs

        // get a copy of old transaction builder
        Transaction.Builder txCopyBuilder = trimedCopy(txBuilder);

        byte[] privKeyBytes = HashUtil.fromECDSAPrivateKey(privateKey);

        // calculate sign value
        buildSignValue(txBuilder, utxoMap, txCopyBuilder, privKeyBytes);
    }

    /**
     * calculate transaction input list's sign value
     * @param txBuilder transaction builder
     * @param utxoMap previous utxos
     * @param txCopyBuilder copied transaction builder(for assemble sign value)
     * @param privKeyBytes user's private key bytes
     */
    private static void buildSignValue(Transaction.Builder txBuilder, Map<String, UTXO> utxoMap, Transaction.Builder txCopyBuilder, byte[] privKeyBytes) {
        List<TXInput> txCopyInputs = txCopyBuilder.getVinList();
        TXInput.Builder tmpTxBuilder = null;
        TXInput txCopyInput = null;
        ByteString oldPubKey = null;
        for (int i = 0; i < txCopyInputs.size(); i++) {
            txCopyInput = txCopyInputs.get(i);
            tmpTxBuilder = txCopyInput.toBuilder();
            oldPubKey = tmpTxBuilder.getPubKey();
            UTXO utxo = utxoMap.get(HexUtil.toHex(txCopyInput.getTxid()) + "-" + txCopyInput.getVout());
            // temporarily add pubKeyHash to pubKey property
            tmpTxBuilder.setPubKey(utxo.getPublicKeyHash());
            txCopyBuilder.setVin(i, tmpTxBuilder.build());

            // get copy's hash value
            byte[] txCopyHash = hashTransaction(txCopyBuilder);

            // recover old pubKey
            tmpTxBuilder.setPubKey(oldPubKey);
            txCopyBuilder.setVin(i, tmpTxBuilder.build());

            byte[] signature = HashUtil.secp256k1Sign(txCopyHash, privKeyBytes);

            // fill signature to each original transaction input
            updateOriginalInputSignature(txBuilder, i, signature);
        }
    }

    /**
     * Update original transaction data with vin's signature.
     * @param txBuilder original transaction
     * @param index vin index
     * @param signature vin signature
     */
    private static void updateOriginalInputSignature(Transaction.Builder txBuilder, int index, byte[] signature) {
        TXInput.Builder txInputBuilder = txBuilder.getVin(index).toBuilder()
                .setSignature(ByteString.copyFrom(signature));
        txBuilder.setVin(index, txInputBuilder);
    }

    /**
     * Validate all input refer to a previous transaction's vout
     * @param txInputs transaction input list
     * @param transactionMap previous transaction data map
     */
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
            tmpTxBuilder.clearPubKey();
            tmpTxBuilder.clearSignature();
            txInput = tmpTxBuilder.build();
            txNewInputs.add(txInput);
        }
        // remove all old inputs
        newBuilder.clearVin();
        // add new input list
        newBuilder.addAllVin(txNewInputs);
        return newBuilder;
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

    /**
     * Serialize Transaction object in promissory format
     * @param transaction object
     * @return byte[] serialized data
     */
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
}
