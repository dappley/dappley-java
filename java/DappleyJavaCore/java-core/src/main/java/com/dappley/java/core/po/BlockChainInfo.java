package com.dappley.java.core.po;

import com.dappley.java.core.util.HexUtil;
import com.dappley.java.core.util.SerializeUtil;
import com.google.protobuf.ByteString;
import lombok.Data;

import java.util.List;

/**
 * Block chain info
 */
@Data
public class BlockChainInfo {
    private String tailBlockHash;
    private long blockHeight;
    /**
     * all producers' addresses
     */
    private List<String> producers;

    /**
     * Parse object from byte array
     * @param bytes array
     * @return BlockChainInfo
     */
    public static BlockChainInfo parseBytes(byte[] bytes) {
        return SerializeUtil.decode(bytes, BlockChainInfo.class);
    }

    public void setTailBlockHash(ByteString byteString) {
        this.tailBlockHash = HexUtil.toHex(byteString.toByteArray());
    }

    /**
     * Convert to byte array
     * @return
     */
    public byte[] toByteArray() {
        return SerializeUtil.encode(this);
    }
}
