package com.dappley.java.core.chain;

import com.dappley.java.core.po.SuitableUtxos;
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
    public static SuitableUtxos getSuitableUtxos(List<Utxo> utxos, BigInteger amount) {
        SuitableUtxos suitableUtxos = new SuitableUtxos();

         if (ObjectUtils.isEmpty(utxos)) {
            return null;
        }
        Collections.sort(utxos, new Comparator<Utxo>() {
            @Override
            public int compare(Utxo u1, Utxo u2) {
            int diff =u1.getAmount().compareTo(u2.getAmount());
            if (diff > 0) {
                return -1;
            }else if (diff < 0) {
                return 1;
            }
            return 0;
            }
        });
        List<Utxo> spendables = new ArrayList<>();
        BigInteger accumulated = BigInteger.ZERO;
        BigInteger accumulatedTotal = BigInteger.ZERO;
        for (int i = 0; i < utxos.size(); i++) {
            if (utxos.get(i) == null) {
                continue;
            }
            if (i < 50){
                spendables.add(utxos.get(i));
                accumulated = accumulated.add(utxos.get(i).getAmount());
            }
            accumulatedTotal = accumulatedTotal.add(utxos.get(i).getAmount());
            if ((accumulated.compareTo(amount) >= 0 && utxos.size() < 100)|| (accumulatedTotal.compareTo(amount) >= 0&&i>=50)){
                break;
            }
        }
        if (accumulated.compareTo(amount) < 0) {
            if (accumulatedTotal.compareTo(amount) < 0){
                suitableUtxos.setMsg("utxo is too many should to merge");
            }else {
                // vout is no enough
                suitableUtxos.setMsg("the balance is insufficient");
            }
            return suitableUtxos;
        }
        suitableUtxos.setUtxos(utxos);
        return suitableUtxos;
    }

}
