package com.dappley.java.core.chain;

import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Utils to handle UTXO datas.
 */
@Slf4j
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
        Collections.sort(utxos, new Comparator<Utxo>() {
            @Override
            public int compare(Utxo u1, Utxo u2) {
            int diff =u1.getAmount().compareTo(u2.getAmount());
            if (diff > 0) {
                return 1;
            }else if (diff < 0) {
                return -1;
            }
            return 0;
            }
        });
        List<Utxo> spendables = new ArrayList<>();
        BigInteger accumulated = BigInteger.ZERO;
        for (int i = 0, size = utxos.size(); i < size; i++) {
            if (i == 50){
               break;
            }
            if (utxos.get(i) == null) {
                continue;
            }
            accumulated = accumulated.add(utxos.get(i).getAmount());
            spendables.add(utxos.get(i));
            if (accumulated.compareTo(amount) >= 0) {
                break;
            }
        }
        if (accumulated.compareTo(amount) >= 0) {
            return spendables;
        }
        accumulated.setBit(0);
        spendables.clear();
        for (int i = utxos.size()-1; i >=utxos.size()-50 && i>=0; i--) {
            if (utxos.get(i) == null) {
                continue;
            }
            accumulated = accumulated.add(utxos.get(i).getAmount());
            spendables.add(utxos.get(i));
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
