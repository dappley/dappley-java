package com.dappley.android.sdk.po;

import com.dappley.android.sdk.protobuf.BlockProto;

import lombok.Data;

@Data
public class BlockVo {
    private BlockHeaderVo blockHeaderVo;

    public BlockVo() {
    }

    public BlockVo(BlockProto.Block block) {
        this.setBlockHeader(block.getHeader());
    }

    public void setBlockHeader(BlockProto.BlockHeader blockHeader) {
        blockHeaderVo = new BlockHeaderVo(blockHeader);
    }
}
