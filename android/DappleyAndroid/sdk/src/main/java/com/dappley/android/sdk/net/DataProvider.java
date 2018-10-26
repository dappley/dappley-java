package com.dappley.android.sdk.net;

import com.dappley.android.sdk.po.Block;
import com.dappley.android.sdk.po.Utxo;

import java.util.List;

public interface DataProvider {

    List<Utxo> getUtxos(String address);

    Block getBlockByHash(String hash);

    List<Block> getBlocks(List<String> startHashs, int count);
}
