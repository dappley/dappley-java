package com.dappley.java.core.po;

import com.dappley.java.core.crypto.ShaDigest;
import com.dappley.java.core.protobuf.TransactionBaseProto;
import com.dappley.java.core.protobuf.TransactionProto;
import com.dappley.java.core.util.ByteUtil;
import com.dappley.java.core.util.HexUtil;
import com.dappley.java.core.util.ObjectUtils;
import com.google.protobuf.ByteString;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Transaction object
 */
@Data
@Slf4j
public class Transaction implements Serializable {
    private byte[] id;
    private List<TxInput> txInputs;
    private List<TxOutput> txOutputs;
    private BigInteger tip;
    private BigInteger gasLimit;
    private BigInteger gasPrice;

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
        this.setId(transaction.getId().toByteArray());
        // add vin list
        List<TransactionBaseProto.TXInput> vinList = transaction.getVinList();
        if (vinList != null) {
            List<TxInput> txInputs = new ArrayList<>(vinList.size());
            for (TransactionBaseProto.TXInput vin : vinList) {
                txInputs.add(new TxInput(vin));
            }
            this.setTxInputs(txInputs);
        }
        // add vout list
        List<TransactionBaseProto.TXOutput> voutList = transaction.getVoutList();
        if (voutList != null) {
            List<TxOutput> txOutputs = new ArrayList<>(voutList.size());
            for (TransactionBaseProto.TXOutput vout : voutList) {
                txOutputs.add(new TxOutput(vout));
            }
            this.setTxOutputs(txOutputs);
        }
        if (transaction.getTip() != null && transaction.getTip().size() > 0) {
            this.setTip(new BigInteger(1, transaction.getTip().toByteArray()));
        }
        if (transaction.getGasLimit() != null && transaction.getGasLimit().size() > 0) {
            this.setGasLimit(new BigInteger(1, transaction.getGasLimit().toByteArray()));
        }
        if (transaction.getGasPrice() != null && transaction.getGasPrice().size() > 0) {
            this.setGasPrice(new BigInteger(1, transaction.getGasPrice().toByteArray()));
        }
    }

    /**
     * Convert to TransactionProto.Transaction
     * @return TransactionProto.Transaction
     */
    public TransactionProto.Transaction toProto() {
        TransactionProto.Transaction.Builder builder = TransactionProto.Transaction.newBuilder();
        if (this.getId() != null) {
            builder.setId(ByteString.copyFrom(this.getId()));
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
        if (this.getTip() != null) {
            builder.setTip(ByteString.copyFrom(ByteUtil.bigInteger2Bytes(this.getTip())));
        }
        if (this.getGasLimit() != null) {
            builder.setGasLimit(ByteString.copyFrom(ByteUtil.bigInteger2Bytes(this.getGasLimit())));
        }
        if (this.getGasPrice() != null) {
            builder.setGasPrice(ByteString.copyFrom(ByteUtil.bigInteger2Bytes(this.getGasPrice())));
        }
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
                txNewInput.setPublicKey(txInput.getPublicKey());
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
                txNewOutput.setPublicKeyHash(txOutput.getPublicKeyHash());
                txNewOutput.setValue(txOutput.getValue());
                txNewOutput.setContract(txOutput.getContract());
                txNewOutputs.add(txNewOutput);
            }
            transaction.setTxOutputs(txNewOutputs);
        }

        transaction.setTip(this.tip);
        transaction.setGasLimit(this.gasLimit);
        transaction.setGasPrice(this.gasPrice);
        return transaction;
    }

    /**
     * trimedCopy creates a trimmed deepClone of Transaction to be used in signing
     * @return Transaction new transaction
     */
    public Transaction trimedCopy() {
        Transaction newTransaction = this.deepClone();
        List<TxInput> txInputs = newTransaction.getTxInputs();
        if (ObjectUtils.isEmpty(txInputs)) {
            return null;
        }
        for (TxInput txInput : txInputs) {
            // clear pubKey and signature
            txInput.setPublicKey(null);
            txInput.setSignature(null);
        }
        return newTransaction;
    }

    /**
     * Returns hash of this object.
     * <p>First use serialize method to convert this one to a byte array.</p>
     * <p>Then call sha256 hash digest to get a hash value.</p>
     * @return byte[]
     */
    public byte[] hash() {
        byte[] oldId = this.getId();
        // clear id property
        this.setId(ByteUtil.EMPTY_BYTE);

        // serialize the new transaction oject
        byte[] serialized = serialize();
        // get sha256 hash
        byte[] hash = ShaDigest.sha256(serialized);
        this.setId(oldId);
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
                bytesList.add(txInput.getPublicKey() == null ? null : txInput.getPublicKey());
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
                bytesList.add(txOutput.getPublicKeyHash() == null ? null : txOutput.getPublicKeyHash());
                bytesList.add(txOutput.getContract() == null ? null : ByteUtil.string2Bytes(txOutput.getContract()));
            }
        }
        // add tip
        if (this.getTip() != null) {
            bytesList.add(ByteUtil.bigInteger2Bytes(this.getTip()));
        }

        if (this.getGasLimit() != null) {
            bytesList.add(ByteUtil.bigInteger2Bytes(this.getGasLimit()));
        }
        if (this.getGasPrice() != null) {
            bytesList.add(ByteUtil.bigInteger2Bytes(this.getGasPrice()));
        }

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

    /**
     * Returns if the transaction is Coinbase.
     * @return boolean true/false
     */
    public boolean isCoinbase() {
        // len(tx.Vin) == 1 && len(tx.Vin[0].Txid) == 0 && tx.Vin[0].Vout == -1 && len(tx.Vout) == 1
        if (this.getTxInputs() == null || this.getTxInputs().size() != 1) {
            return false;
        }
        if (this.getTxInputs().get(0).getTxId() != null && this.getTxInputs().get(0).getTxId().length > 0) {
            return false;
        }
        if (this.getTxInputs().get(0).getVout() != -1) {
            return false;
        }
        if (this.getTxOutputs() == null || this.getTxOutputs().size() != 1) {
            return false;
        }
        return true;
    }
}
