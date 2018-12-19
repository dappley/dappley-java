package com.dappley.android.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.java.core.net.DataProvider;
import com.dappley.java.core.net.ProtocalProvider;
import com.dappley.android.sdk.task.LocalBlockSchedule;
import com.tencent.mmkv.MMKV;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LocalBlockScheduleTest {

    @Test
    public void getBlocks() {
        Context context = InstrumentationRegistry.getTargetContext();
        MMKV.initialize(context);

        BlockChainManager.initGenesisBlock(context);

        LocalBlockSchedule.start(context);
    }
}
