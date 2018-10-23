package com.dappley.android.sdk.po;

import com.dappley.android.sdk.protobuf.RpcProto;
import com.google.protobuf.ByteString;

import lombok.Data;

/**
 * UTXO object
 */
@Data
public class Utxo {
    private long amount;
    private byte[] publicKeyHash;
    private byte[] txId;
    private int txIndex;

    public Utxo() {
    }

    /**
     * Constructor with RpcProto.UTXO
     * @param utxo
     */
    public Utxo(RpcProto.UTXO utxo) {
        this.fromProto(utxo);
    }

    /**
     * Parse RpcProto.UTXO to this object.
     * @param utxo RpcProto.UTXO
     */
    public void fromProto(RpcProto.UTXO utxo) {
        this.setAmount(utxo.getAmount());
        this.setPublicKeyHash(utxo.getPublicKeyHash().toByteArray());
        this.setTxId(utxo.getTxid().toByteArray());
        this.setTxIndex(utxo.getTxIndex());
    }

    /**
     * Convert to RpcProto.UTXO
     * @return RpcProto.UTXO
     */
    public RpcProto.UTXO toProto() {
        RpcProto.UTXO.Builder builder = RpcProto.UTXO.newBuilder();
        builder.setAmount(this.getAmount());
        builder.setPublicKeyHash(ByteString.copyFrom(this.getTxId()));
        builder.setTxid(ByteString.copyFrom(this.getTxId()));
        builder.setTxIndex(this.getTxIndex());
        return builder.build();
    }

}
