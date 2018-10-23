package com.dappley.android.sdk.po;

import com.dappley.android.sdk.protobuf.TransactionProto;
import com.google.protobuf.ByteString;

import lombok.Data;

/**
 * Transaction Input
 */
@Data
public class TxInput {
    private byte[] txId;
    private int vout;
    private byte[] signature;
    private byte[] pubKey;

    public TxInput() {
    }

    /**
     * Constructor with TransactionProto.TXInput
     * @param txInput
     */
    public TxInput(TransactionProto.TXInput txInput) {
        this.parseProto(txInput);
    }

    /**
     * Parse TransactionProto.TXInput to this object.
     * @param txInput
     */
    public void parseProto(TransactionProto.TXInput txInput) {
        this.setTxId(txInput.getTxid() == null ? null : txInput.getTxid().toByteArray());
        this.setVout(txInput.getVout());
        this.setSignature(txInput.getSignature() == null ? null : txInput.getSignature().toByteArray());
        this.setPubKey(txInput.getPubKey() == null ? null : txInput.getPubKey().toByteArray());
    }

    /**
     * Convert to TransactionProto.TXInput
     * @return TransactionProto.TXInput
     */
    public TransactionProto.TXInput toProto() {
        TransactionProto.TXInput.Builder builder = TransactionProto.TXInput.newBuilder();
        if (this.getTxId() != null) {
            builder.setTxid(ByteString.copyFrom(this.getTxId()));
        }
        builder.setVout(this.getVout());
        if (this.getSignature() != null) {
            builder.setSignature(ByteString.copyFrom(this.getSignature()));
        }
        if (this.getPubKey() != null) {
            builder.setPubKey(ByteString.copyFrom(this.getPubKey()));
        }
        return builder.build();
    }
}
