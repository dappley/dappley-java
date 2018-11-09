package com.dappley.android.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dappley.android.sdk.chain.BlockChainManager;
import com.dappley.android.sdk.db.UtxoDb;
import com.dappley.android.sdk.db.UtxoIndexDb;
import com.dappley.android.sdk.net.ProtocalProvider;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.po.UtxoIndex;
import com.tencent.mmkv.MMKV;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class LocalUtxoTest {

    @Test
    public void testLocal() {
        String address = "1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG";
        Context context = InstrumentationRegistry.getTargetContext();
        MMKV.initialize(context);

//        BlockChainManager.initGenesisBlock(context);
        UtxoIndexDb utxoIndexDb = new UtxoIndexDb(context);
        UtxoDb utxoDb = new UtxoDb(context);
        Set<UtxoIndex> utxoIndexSet = utxoIndexDb.get(address);
        Assert.assertTrue(utxoIndexSet != null && utxoIndexSet.size() > 0);
    }
}
