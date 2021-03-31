package com.dappley.java.core.po;

import java.util.List;

public class SuitableUtxos {
    private List<Utxo> utxos;
    /**
     * Error message
     */
    private String msg;

    public void setUtxos(List<Utxo> utxos) {
        this.utxos = utxos;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Utxo> getUtxos() {
        return utxos;
    }

    public String getMsg() {
        return msg;
    }
}
