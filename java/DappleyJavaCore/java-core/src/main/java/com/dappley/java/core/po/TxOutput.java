package com.dappley.java.core.po;

import com.dappley.java.core.protobuf.TransactionBaseProto;
import com.dappley.java.core.protobuf.TransactionProto;
import com.google.protobuf.ByteString;
import lombok.Data;

import java.io.Serializable;

/**
 * Transaction Output
 */
@Data
public class TxOutput implements Serializable {
    private byte[] value;
    private byte[] publicKeyHash;
    private String contract;

    public TxOutput() {
    }

    /**
     * Constructor with TransactionProto.TXOutput
     * @param txOutput
     */
    public TxOutput(TransactionBaseProto.TXOutput txOutput) {
        this.parseProto(txOutput);
    }

    /**
     * Parse TransactionProto.TXOutput to this object.
     * @param txOutput
     */
    public void parseProto(TransactionBaseProto.TXOutput txOutput) {
        this.setValue(txOutput.getValue() == null ? null : txOutput.getValue().toByteArray());
        this.setPublicKeyHash(txOutput.getPublicKeyHash() == null ? null : txOutput.getPublicKeyHash().toByteArray());
        this.setContract(txOutput.getContract());
    }

    /**
     * Convert to TransactionProto.TXOutput
     * @return TransactionProto.TXOutput
     */
    public TransactionBaseProto.TXOutput toProto() {
        TransactionBaseProto.TXOutput.Builder builder = TransactionBaseProto.TXOutput.newBuilder();
        if (this.getValue() != null) {
            builder.setValue(ByteString.copyFrom(this.getValue()));
        }
        if (this.getPublicKeyHash() != null) {
            builder.setPublicKeyHash(ByteString.copyFrom(this.getPublicKeyHash()));
        }
        builder.setContract(this.getContract() == null ? "" : this.getContract());
        return builder.build();
    }
}
