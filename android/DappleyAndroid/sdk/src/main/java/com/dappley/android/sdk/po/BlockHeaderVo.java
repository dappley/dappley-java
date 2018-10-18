package com.dappley.android.sdk.po;

import com.dappley.android.sdk.protobuf.BlockProto;

import lombok.Data;

@Data
public class BlockHeaderVo {
    private String hash;
    private String prevHash;
    private long height;
    private long nonce;
    private String sign;
    private long timestamp;

    public BlockHeaderVo() {
    }

    public BlockHeaderVo(BlockProto.BlockHeader blockHeader) {
        this.hash = blockHeader.getHash().toStringUtf8();
        this.height = blockHeader.getHeight();
        this.nonce = blockHeader.getNonce();
        this.prevHash = blockHeader.getPrevhash().toStringUtf8();
        this.sign = blockHeader.getSign().toStringUtf8();
        this.timestamp = blockHeader.getTimestamp();
    }
}
