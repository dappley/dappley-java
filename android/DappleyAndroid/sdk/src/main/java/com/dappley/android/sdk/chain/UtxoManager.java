package com.dappley.android.sdk.chain;

import android.util.Log;

import com.dappley.android.sdk.DappleyClient;
import com.dappley.android.sdk.protobuf.RpcProto;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Utils to handle UTXO datas.
 */
public class UtxoManager {
    private static final String TAG = "UtxoManager";

    public static List<RpcProto.UTXO> getSpendableUtxos(String address, int amount) {
        try {
            List<RpcProto.UTXO> all = DappleyClient.getUtxo(address);
            return getSpendableUtxos(all, amount);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getSpendableUtxos: ", e);
        }
        return null;
    }

    public static List<RpcProto.UTXO> getSpendableUtxos(List<RpcProto.UTXO> utxos, int amount) {
        if (CollectionUtils.isEmpty(utxos)) {
            return null;
        }
        List<RpcProto.UTXO> spendables = new ArrayList<>();
        int accumulated = 0;
        for (RpcProto.UTXO utxo : utxos) {
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
