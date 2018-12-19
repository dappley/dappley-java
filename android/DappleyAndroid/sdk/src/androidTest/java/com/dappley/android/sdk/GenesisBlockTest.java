package com.dappley.android.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dappley.android.sdk.config.Configuration;
import com.dappley.java.core.chain.BlockManager;
import com.dappley.android.sdk.db.BlockDb;
import com.dappley.java.core.net.ProtocalProvider;
import com.dappley.java.core.net.RpcProtocalProvider;
import com.dappley.java.core.po.Block;
import com.dappley.java.core.util.HexUtil;
import com.tencent.mmkv.MMKV;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;

@RunWith(AndroidJUnit4.class)
public class GenesisBlockTest {

    @Test
    public void newGenesis() {
        Context context = InstrumentationRegistry.getTargetContext();
        String serverIp = Configuration.getInstance(context).getServerIp();
        int serverPort = Configuration.getInstance(context).getServerPort();
        MMKV.initialize(context);

        ProtocalProvider protocalProvider = new RpcProtocalProvider();
        protocalProvider.init(serverIp, serverPort);
        Block blockOnline = new Block(protocalProvider.getBlockByHeight(0));
        System.out.println(blockOnline.toString());

        Block block = BlockManager.newGenesisBlock();

        System.out.println(block.toString());
        System.out.println(HexUtil.toHex(block.getHeader().getHash()));

        BlockDb blockDB = new BlockDb(context);
        blockDB.save(block);

        block = blockDB.get(HexUtil.toHex(block.getHeader().getHash()));
        System.out.println(HexUtil.toHex(block.getHeader().getHash()));

        Assert.assertEquals(HexUtil.toHex(block.getHeader().getHash()), HexUtil.toHex(blockOnline.getHeader().getHash()));
    }
}
