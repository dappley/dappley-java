package com.dappley.android.sdk;

import com.dappley.android.sdk.po.UtxoIndex;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class UtxoIndexEqualsTest {

    @Test
    public void testEquals() {
        UtxoIndex index1 = new UtxoIndex();
        index1.setTxId("1");
        index1.setVoutIndex(1);
        UtxoIndex index2 = new UtxoIndex();
        index2.setTxId("1");
        index2.setVoutIndex(1);
        Assert.assertEquals(index1, index2);
    }

    @Test
    public void testSet() {
        Set<UtxoIndex> sets = new HashSet<>();
        UtxoIndex index1 = new UtxoIndex();
        index1.setTxId("1");
        index1.setVoutIndex(1);
        UtxoIndex index2 = new UtxoIndex();
        index2.setTxId("1");
        index2.setVoutIndex(1);
        sets.add(index1);
        sets.add(index2);
        Assert.assertEquals(1, sets.size());
    }
}
