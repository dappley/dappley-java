package com.dappley.android.sdk.po;

import com.dappley.android.sdk.protobuf.RpcProto;
import com.dappley.android.sdk.util.SerializeUtil;
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
        this.parseProto(utxo);
    }

    /**
     * Recovery from serialized bytes.
     * @param bytes byte array
     * @return Utxo object
     */
    public static Utxo parseBytes(byte[] bytes) {
        return SerializeUtil.decode(bytes, Utxo.class);
    }

    /**
     * Parse RpcProto.UTXO to this object.
     * @param utxo RpcProto.UTXO
     */
    public void parseProto(RpcProto.UTXO utxo) {
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

    public byte[] toByteArray() {
        return SerializeUtil.encode(this);
    }
}
