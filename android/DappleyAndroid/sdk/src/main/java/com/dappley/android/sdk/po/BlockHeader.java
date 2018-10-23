package com.dappley.android.sdk.po;

import com.dappley.android.sdk.protobuf.BlockProto;
import com.google.protobuf.ByteString;

import lombok.Data;

/**
 * Block info
 */
@Data
public class BlockHeader {
    /**
     * block hash value
     */
    private byte[] hash;
    /**
     * Previous block hash value
     */
    private byte[] prevHash;
    private long nonce;
    private long timestamp;
    /**
     * block sign value
     */
    private byte[] sign;
    private long height;

    public BlockHeader() {
    }

    /**
     * Constructor with BlockProto.BlockHeader
     * @param blockHeader BlockProto.BlockHeader
     */
    public BlockHeader(BlockProto.BlockHeader blockHeader) {
        this.parseProto(blockHeader);
    }

    /**
     * Parse BlockProto.BlockHeader to this object.
     * @param blockHeader
     */
    public void parseProto(BlockProto.BlockHeader blockHeader) {
        this.setHash(blockHeader.getHash() == null ? null : blockHeader.getHash().toByteArray());
        this.setPrevHash(blockHeader.getPrevhash() == null ? null : blockHeader.getPrevhash().toByteArray());
        this.setNonce(blockHeader.getNonce());
        this.setTimestamp(blockHeader.getTimestamp());
        this.setSign(blockHeader.getSign() == null ? null : blockHeader.getSign().toByteArray());
        this.setHeight(blockHeader.getHeight());
    }

    /**
     * Convert to BlockProto.BlockHeader
     * @return
     */
    public BlockProto.BlockHeader toProto() {
        BlockProto.BlockHeader.Builder builder = BlockProto.BlockHeader.newBuilder();
        if (this.getHash() != null) {
            builder.setHash(ByteString.copyFrom(this.getHash()));
        }
        if (this.getPrevHash() != null) {
            builder.setPrevhash(ByteString.copyFrom(this.getPrevHash()));
        }
        builder.setNonce(this.getNonce());
        builder.setTimestamp(this.getTimestamp());
        if (this.getSign() != null) {
            builder.setSign(ByteString.copyFrom(this.getSign()));
        }
        builder.setHeight(this.getHeight());
        return builder.build();
    }
}
