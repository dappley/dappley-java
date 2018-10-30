package com.dappley.android.sdk.chain;

import android.util.Log;

import com.dappley.android.sdk.DappleyClient;
import com.dappley.android.sdk.po.Utxo;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils to handle UTXO datas.
 */
public class UtxoManager {
    private static final String TAG = "UtxoManager";

    /**
     * Returns suitable utxo list of the address
     * @param address user's account address
     * @param amount target vout amount
     * @return List<Utxo> unspend vouts meet the need for target amount
     */
    public static List<Utxo> getSpendableUtxos(String address, BigInteger amount) {
        try {
            List<Utxo> all = DappleyClient.getUtxo(address);
            return getSpendableUtxos(all, amount);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getSpendableUtxos: ", e);
        }
        return null;
    }

    /**
     * Reform utxo list of target amount
     * @param utxos all user's utxo
     * @param amount target amount
     * @return List<Utxo> suitable utxo list
     */
    public static List<Utxo> getSpendableUtxos(List<Utxo> utxos, BigInteger amount) {
        if (CollectionUtils.isEmpty(utxos)) {
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
            throw new IllegalArgumentException("there is no enough vout");
        }
        return spendables;
    }
}
