package com.dappley.java.core.po;

import com.dappley.java.core.protobuf.TransactionProto;
import com.google.protobuf.ByteString;

import java.io.Serializable;

import lombok.Data;

/**
 * Transaction Output
 */
@Data
public class TxOutput implements Serializable {
    private byte[] value;
    private byte[] pubKeyHash;
    private String contract;

    public TxOutput() {
    }

    /**
     * Constructor with TransactionProto.TXOutput
     * @param txOutput
     */
    public TxOutput(TransactionProto.TXOutput txOutput) {
        this.parseProto(txOutput);
    }

    /**
     * Parse TransactionProto.TXOutput to this object.
     * @param txOutput
     */
    public void parseProto(TransactionProto.TXOutput txOutput) {
        this.setValue(txOutput.getValue() == null ? null : txOutput.getValue().toByteArray());
        this.setPubKeyHash(txOutput.getPubKeyHash() == null ? null : txOutput.getPubKeyHash().toByteArray());
        this.setContract(txOutput.getContract());
    }

    /**
     * Convert to TransactionProto.TXOutput
     * @return TransactionProto.TXOutput
     */
    public TransactionProto.TXOutput toProto() {
        TransactionProto.TXOutput.Builder builder = TransactionProto.TXOutput.newBuilder();
        if (this.getValue() != null) {
            builder.setValue(ByteString.copyFrom(this.getValue()));
        }
        if (this.getPubKeyHash() != null) {
            builder.setPubKeyHash(ByteString.copyFrom(this.getPubKeyHash()));
        }
        builder.setContract(this.getContract() == null ? "" : this.getContract());
        return builder.build();
    }
}
