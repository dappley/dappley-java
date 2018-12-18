package com.dappley.java.test;

import com.dappley.java.core.chain.BlockManager;
import com.dappley.java.core.po.Block;
import com.dappley.java.core.util.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class GenesisBlockTest {

    @Test
    public void newGenesis() {
        Block block = BlockManager.newGenesisBlock();

        log.debug(block.toString());
        log.debug(HexUtil.toHex(block.getHeader().getHash()));

        Assert.assertTrue(HexUtil.toHex(block.getHeader().getHash()).length() > 0);
    }
}
