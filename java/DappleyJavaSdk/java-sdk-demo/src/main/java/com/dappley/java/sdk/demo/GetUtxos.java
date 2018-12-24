package com.dappley.java.sdk.demo;

import com.dappley.java.core.po.Utxo;
import com.dappley.java.sdk.Dappley;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Query utxo list of the wallet address.
 * <p>Before call <code>Dappley.getUtxos</code>, call <code>Dappley.init</code> first.</p>
 */
@Slf4j
public class GetUtxos {
    public static void main(String[] args) {
        Dappley.init(Dappley.DataMode.REMOTE_ONLINE);
        String address = "dastXXWLe5pxbRYFhcyUq8T3wb5srWkHKa";
        List<Utxo> utxos = Dappley.getUtxos(address, 1, 10);
        log.info("utxos size:" + utxos.size());
    }
}
