package com.dappley.java.core.po;

import com.dappley.java.core.protobuf.RpcProto;
import com.dappley.java.core.util.ByteUtil;
import com.dappley.java.core.util.SerializeUtil;
import com.google.protobuf.ByteString;

import java.math.BigInteger;

import lombok.Data;

/**
 * UTXO object
 */
@Data
public class Utxo {
    private BigInteger amount;
    private byte[] publicKeyHash;
    private byte[] txId;
    private int voutIndex;

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
        if (utxo.getAmount() != null && utxo.getAmount().size() > 0) {
            this.setAmount(new BigInteger(1, utxo.getAmount().toByteArray()));
        } else {
            this.setAmount(BigInteger.ZERO);
        }
        this.setPublicKeyHash(utxo.getPublicKeyHash().toByteArray());
        this.setTxId(utxo.getTxid().toByteArray());
        this.setVoutIndex(utxo.getTxIndex());
    }

    /**
     * Convert to RpcProto.UTXO
     * @return RpcProto.UTXO
     */
    public RpcProto.UTXO toProto() {
        RpcProto.UTXO.Builder builder = RpcProto.UTXO.newBuilder();
        builder.setAmount(ByteString.copyFrom(ByteUtil.bigInteger2Bytes(this.getAmount())));
        builder.setPublicKeyHash(ByteString.copyFrom(this.getTxId()));
        builder.setTxid(ByteString.copyFrom(this.getTxId()));
        builder.setTxIndex(this.getVoutIndex());
        return builder.build();
    }

    public byte[] toByteArray() {
        return SerializeUtil.encode(this);
    }
}
