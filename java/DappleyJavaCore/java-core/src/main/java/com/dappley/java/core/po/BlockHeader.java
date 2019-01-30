package com.dappley.java.core.po;

import com.dappley.java.core.protobuf.BlockProto;
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
    private byte[] previousHash;
    private long nonce;
    private long timestamp;
    /**
     * block sign value
     */
    private byte[] signature;
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
        this.setPreviousHash(blockHeader.getPreviousHash() == null ? null : blockHeader.getPreviousHash().toByteArray());
        this.setNonce(blockHeader.getNonce());
        this.setTimestamp(blockHeader.getTimestamp());
        this.setSignature(blockHeader.getSignature() == null ? null : blockHeader.getSignature().toByteArray());
        this.setHeight(blockHeader.getHeight());
    }

    /**
     * Convert to BlockProto.BlockHeader
     * @return BlockProto.BlockHeader
     */
    public BlockProto.BlockHeader toProto() {
        BlockProto.BlockHeader.Builder builder = BlockProto.BlockHeader.newBuilder();
        if (this.getHash() != null) {
            builder.setHash(ByteString.copyFrom(this.getHash()));
        }
        if (this.getPreviousHash() != null) {
            builder.setPreviousHash(ByteString.copyFrom(this.getPreviousHash()));
        }
        builder.setNonce(this.getNonce());
        builder.setTimestamp(this.getTimestamp());
        if (this.getSignature() != null) {
            builder.setSignature(ByteString.copyFrom(this.getSignature()));
        }
        builder.setHeight(this.getHeight());
        return builder.build();
    }
}
