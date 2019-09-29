package com.dappley.java.core.po;

import com.dappley.java.core.protobuf.TransactionBaseProto;
import com.google.protobuf.ByteString;
import lombok.Data;

import java.io.Serializable;

/**
 * Transaction Input
 */
@Data
public class TxInput implements Serializable {
    private byte[] txId;
    private int vout;
    private byte[] signature;
    private byte[] publicKey;

    public TxInput() {
    }

    /**
     * Constructor with TransactionProto.TXInput
     * @param txInput
     */
    public TxInput(TransactionBaseProto.TXInput txInput) {
        this.parseProto(txInput);
    }

    /**
     * Parse TransactionProto.TXInput to this object.
     * @param txInput
     */
    public void parseProto(TransactionBaseProto.TXInput txInput) {
        this.setTxId(txInput.getTxid() == null ? null : txInput.getTxid().toByteArray());
        this.setVout(txInput.getVout());
        this.setSignature(txInput.getSignature() == null ? null : txInput.getSignature().toByteArray());
        this.setPublicKey(txInput.getPublicKey() == null ? null : txInput.getPublicKey().toByteArray());
    }

    /**
     * Convert to TransactionProto.TXInput
     * @return TransactionProto.TXInput
     */
    public TransactionBaseProto.TXInput toProto() {
        TransactionBaseProto.TXInput.Builder builder = TransactionBaseProto.TXInput.newBuilder();
        if (this.getTxId() != null) {
            builder.setTxid(ByteString.copyFrom(this.getTxId()));
        }
        builder.setVout(this.getVout());
        if (this.getSignature() != null) {
            builder.setSignature(ByteString.copyFrom(this.getSignature()));
        }
        if (this.getPublicKey() != null) {
            builder.setPublicKey(ByteString.copyFrom(this.getPublicKey()));
        }
        return builder.build();
    }
}
