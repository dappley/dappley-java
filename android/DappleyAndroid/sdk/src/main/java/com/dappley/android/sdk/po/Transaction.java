package com.dappley.android.sdk.po;

import com.dappley.android.sdk.crypto.ShaDigest;
import com.dappley.android.sdk.protobuf.TransactionProto;
import com.dappley.android.sdk.util.ByteUtil;
import com.google.protobuf.ByteString;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * Transaction object
 */
@Data
public class Transaction {
    private byte[] id;
    private List<TxInput> txInputs;
    private List<TxOutput> txOutputs;
    private long tip;

    public Transaction() {
    }

    /**
     * Constructor with TransactionProto.Transaction.
     * @param transaction
     */
    public Transaction(TransactionProto.Transaction transaction) {
        this.parseProto(transaction);
    }

    /**
     * Parse TransactionProto.Transaction to this object.
     * @param transaction
     */
    public void parseProto(TransactionProto.Transaction transaction) {
        this.setId(transaction.getID().toByteArray());
        // add vin list
        List<TransactionProto.TXInput> vinList = transaction.getVinList();
        if (vinList != null) {
            List<TxInput> txInputs = new ArrayList<>(vinList.size());
            for (TransactionProto.TXInput vin : vinList) {
                txInputs.add(new TxInput(vin));
            }
            this.setTxInputs(txInputs);
        }
        // add vout list
        List<TransactionProto.TXOutput> voutList = transaction.getVoutList();
        if (voutList != null) {
            List<TxOutput> txOutputs = new ArrayList<>(voutList.size());
            for (TransactionProto.TXOutput vout : voutList) {
                txOutputs.add(new TxOutput(vout));
            }
            this.setTxOutputs(txOutputs);
        }
        this.setTip(transaction.getTip());
    }

    /**
     * Convert to TransactionProto.Transaction
     * @return
     */
    public TransactionProto.Transaction toProto() {
        TransactionProto.Transaction.Builder builder = TransactionProto.Transaction.newBuilder();
        if (this.getId() != null) {
            builder.setID(ByteString.copyFrom(this.getId()));
        }

        List<TxInput> txInputs = this.getTxInputs();
        if (txInputs != null) {
            for (TxInput txInput : txInputs) {
                builder.addVin(txInput.toProto());
            }
        }

        List<TxOutput> txOutputs = this.getTxOutputs();
        if (txOutputs != null) {
            for (TxOutput txOutput : txOutputs) {
                builder.addVout(txOutput.toProto());
            }
        }
        builder.setTip(this.getTip());
        return builder.build();
    }

    /**
     * Add a TxInput into Transaction
     * @param txInput
     */
    public void addTxInput(TxInput txInput) {
        if (txInputs == null) {
            txInputs = new ArrayList<>();
        }
        txInputs.add(txInput);
    }

    /**
     * Add a TxOutput into transaction
     * @param txOutput
     */
    public void addTxOutput(TxOutput txOutput) {
        if (txOutputs == null) {
            txOutputs = new ArrayList<>();
        }
        txOutputs.add(txOutput);
    }

    /**
     * Got a deep clone object of this one.
     * @return Transaction copied transaction
     */
    public Transaction deepClone() {
        Transaction transaction = new Transaction();
        transaction.setId(this.id.clone());

        // deep clone list datas
        if (this.txInputs != null) {
            TxInput txNewInput;
            List<TxInput> txNewInputs = new ArrayList<>();
            for (TxInput txInput : txInputs) {
                txNewInput = new TxInput();
                txNewInput.setTxId(txInput.getTxId());
                txNewInput.setPubKey(txInput.getPubKey());
                txNewInput.setSignature(txInput.getSignature());
                txNewInput.setVout(txInput.getVout());
                txNewInputs.add(txNewInput);
            }
            transaction.setTxInputs(txNewInputs);
        }

        if (this.txOutputs != null) {
            TxOutput txNewOutput;
            List<TxOutput> txNewOutputs = new ArrayList<>();
            for (TxOutput txOutput : txOutputs) {
                txNewOutput = new TxOutput();
                txNewOutput.setPubKeyHash(txOutput.getPubKeyHash());
                txNewOutput.setValue(txOutput.getValue());
                txNewOutputs.add(txNewOutput);
            }
            transaction.setTxOutputs(txNewOutputs);
        }

        transaction.setTip(this.tip);
        return transaction;
    }

    /**
     * trimedCopy creates a trimmed deepClone of Transaction to be used in signing
     * @return Transaction new transaction
     */
    public Transaction trimedCopy() {
        Transaction newTransaction = this.deepClone();
        List<TxInput> txInputs = newTransaction.getTxInputs();
        if (CollectionUtils.isEmpty(txInputs)) {
            return null;
        }
        for (TxInput txInput : txInputs) {
            // clear pubKey and signature
            txInput.setPubKey(null);
            txInput.setSignature(null);
        }
        return newTransaction;
    }

    /**
     * Returns hash of this object.
     * <p>First use serialize method to convert this one to a byte array.</p>
     * <p>Then call sha256 hash digest to get a hash value.</p>
     * @return
     */
    public byte[] hash() {
        // clear id property
        this.setId(ByteUtil.EMPTY_BYTE);

        // serialize the new transaction oject
        byte[] serialized = serialize();
        // get sha256 hash
        byte[] hash = ShaDigest.sha256(serialized);
        return hash;
    }

    /**
     * Serialize Transaction object in promissory format
     * @return byte[] serialized data
     */
    public byte[] serialize() {
        // serialize transaction
        List<byte[]> bytesList = new LinkedList<>();

        // add vin
        List<TxInput> txInputs = this.getTxInputs();
        if (txInputs != null) {
            for (TxInput txInput : txInputs) {
                // txid
                bytesList.add(txInput.getTxId() == null ? null : txInput.getTxId());
                // vout
                bytesList.add(ByteUtil.int2Bytes(txInput.getVout()));
                // pubkey
                bytesList.add(txInput.getPubKey() == null ? null : txInput.getPubKey());
                // signature
                bytesList.add(txInput.getSignature() == null ? null : txInput.getSignature());
            }
        }

        // add vout
        List<TxOutput> txOutputs = this.getTxOutputs();
        if (txOutputs != null) {
            for (TxOutput txOutput : txOutputs) {
                // value
                bytesList.add(txOutput.getValue() == null ? null : txOutput.getValue());
                // pubKeyHash
                bytesList.add(txOutput.getPubKeyHash() == null ? null : txOutput.getPubKeyHash());
            }
        }
        // add tip
        bytesList.add(ByteUtil.long2Bytes(this.getTip()));

        return ByteUtil.joinBytes(bytesList);
    }

    /**
     * Generate ID of Transaction object
     * <p>The ID is a byte array</p>
     */
    public void createId() {
        byte[] hash = this.hash();
        this.setId(hash);
    }
}
