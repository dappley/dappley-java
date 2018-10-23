package com.dappley.android.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dappley.android.sdk.chain.BlockManager;
import com.dappley.android.sdk.net.ProtocalProvider;
import com.dappley.android.sdk.po.Block;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;

@RunWith(AndroidJUnit4.class)
public class GenesisBlockTest {

    @Test
    public void newGenesis() throws UnsupportedEncodingException, IllegalAccessException {
        Context context = InstrumentationRegistry.getTargetContext();
        DappleyClient.init(context, ProtocalProvider.ProviderType.RPC);

        Block blockOnline = DappleyClient.getBlockByHeight(0);
        System.out.println(blockOnline.toString());

        Block block = BlockManager.newGenesisBlock();
        System.out.println(block.toString());
    }
}
