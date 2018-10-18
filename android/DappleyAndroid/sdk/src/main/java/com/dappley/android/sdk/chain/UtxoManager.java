package com.dappley.android.sdk.chain;

import android.util.Log;

import com.dappley.android.sdk.DappleyClient;
import com.dappley.android.sdk.protobuf.RpcProto.UTXO;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utils to handle UTXO datas.
 */
public class UtxoManager {
    private static final String TAG = "UtxoManager";

    public static List<UTXO> getSpendableUtxos(String address, int amount) {
        try {
            List<UTXO> all = DappleyClient.getUtxo(address);
            return getSpendableUtxos(all, amount);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getSpendableUtxos: ", e);
        }
        return null;
    }

    public static List<UTXO> getSpendableUtxos(List<UTXO> utxos, int amount) {
        if (CollectionUtils.isEmpty(utxos)) {
            return null;
        }
        List<UTXO> spendables = new ArrayList<>();
        int accumulated = 0;
        for (UTXO utxo : utxos) {
            if (utxo == null) {
                continue;
            }
            accumulated += utxo.getAmount();
            spendables.add(utxo);
            if (accumulated >= amount) {
                break;
            }
        }
        return spendables;
    }
}
