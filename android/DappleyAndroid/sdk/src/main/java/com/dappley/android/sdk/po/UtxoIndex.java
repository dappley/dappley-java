package com.dappley.android.sdk.po;

import java.io.Serializable;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object for utxo index.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtxoIndex implements Serializable{
    /**
     * Related transaction id
     */
    private String txId;
    /**
     * Related TxOutPut index
     */
    private int voutIndex;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (txId != null) {
            sb.append(txId);
        }
        sb.append("-").append(voutIndex);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int code = Arrays.hashCode(new Object[]{txId, voutIndex});
        return code;
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof UtxoIndex) {
            UtxoIndex another = (UtxoIndex) anObject;
            return this.toString().equals(another.toString());
        }
        return false;
    }

}
