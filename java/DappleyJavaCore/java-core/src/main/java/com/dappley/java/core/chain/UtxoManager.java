package com.dappley.java.core.chain;

import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.util.ObjectUtils;

import java.math.BigInteger;
import java.util.ArrayList;
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
        if (ObjectUtils.isEmpty(utxos)) {
            return null;
        }
        List<Utxo> spendables = new ArrayList<>();
        BigInteger accumulated = BigInteger.ZERO;
        for (Utxo utxo : utxos) {
            if (utxo == null) {
                continue;
            }
            accumulated = accumulated.add(utxo.getAmount());
            spendables.add(utxo);
            if (accumulated.compareTo(amount) >= 0) {
                break;
            }
        }
        if (accumulated.compareTo(amount) < 0) {
            // vout is no enough
            return null;
        }
        return spendables;
    }

}
