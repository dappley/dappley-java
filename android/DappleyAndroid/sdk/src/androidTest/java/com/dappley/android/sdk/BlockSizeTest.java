package com.dappley.android.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dappley.android.sdk.db.BlockDb;
import com.dappley.android.sdk.db.TransactionDb;
import com.tencent.mmkv.MMKV;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BlockSizeTest {

    @Test
    public void showSize() {
        Context context = InstrumentationRegistry.getTargetContext();
        MMKV.initialize(context);

        BlockDb blockDb = new BlockDb(context);
        TransactionDb transactionDb = new TransactionDb(context);
//        String[] blockKeys = blockDb.getAllKeys();
//        String[] transactionKeys = transactionDb.getAllKeys();
//        if (blockKeys != null) {
//            System.out.println("blockKeys:" + blockKeys.length);
//        }
//        if (transactionKeys != null) {
//            System.out.println("transactionKeys:" + transactionKeys.length);
//        }
//        System.out.println("blockKey size:" + blockDb.getSize());
//        System.out.println("transactionKey size:" + transactionDb.getSize());
    }
}
