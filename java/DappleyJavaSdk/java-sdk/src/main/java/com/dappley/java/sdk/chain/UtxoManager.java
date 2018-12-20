package com.dappley.java.sdk.chain;

import com.dappley.java.core.po.Utxo;

import java.math.BigInteger;
import java.util.List;

/**
 * Utils to handle UTXO datas.
 */
public class UtxoManager {
    private static final String TAG = "UtxoManager";

    /**
     * Reform utxo list of target amount
     * @param utxos  all user's utxo
     * @param amount target amount
     * @return List<Utxo> suitable utxo list
     */
    public static List<Utxo> getSuitableUtxos(List<Utxo> utxos, BigInteger amount) {
        return com.dappley.java.core.chain.UtxoManager.getSuitableUtxos(utxos, amount);
    }

}
