package com.dappley.android.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dappley.android.sdk.config.Configuration;
import com.dappley.java.core.net.DataProvider;
import com.dappley.android.sdk.net.LocalDataProvider;
import com.dappley.java.core.net.ProtocalProvider;
import com.dappley.java.core.net.RemoteDataProvider;
import com.dappley.java.core.po.Utxo;
import com.tencent.mmkv.MMKV;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UtxoTest {

    @Test
    public void testUtxo() {
        Context context = InstrumentationRegistry.getTargetContext();
        String serverIp = Configuration.getInstance(context).getServerIp();
        int serverPort = Configuration.getInstance(context).getServerPort();
        MMKV.initialize(context);

        String address = "1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG";
        DataProvider dataProvider = new LocalDataProvider(context);
        List<Utxo> utxos = dataProvider.getUtxos(address);
        System.out.println(utxos == null ? 0 : utxos.size());

        dataProvider = new RemoteDataProvider(RemoteDataProvider.RemoteProtocalType.RPC, serverIp, serverPort);
        utxos = dataProvider.getUtxos(address);
        System.out.println(utxos == null ? 0 : utxos.size());
    }
}
