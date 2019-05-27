package com.dappley.java.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Coin format util.
 */
public class CoinUtil {

    /**
     * Returns formatted balance in DW unit mode
     * @param balance wallet balance
     * @return String formatted string
     */
    public static String getDw(BigInteger balance) {
        if (balance == null || balance.compareTo(BigInteger.ZERO) <= 0) {
            return "0";
        }
        try {
            BigDecimal divided = new BigDecimal(balance).divide(Constant.COIN_DW).setScale(Constant.COIN_SCALE);
//            return divided.stripTrailingZeros().toPlainString();
            return divided.toPlainString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }
}
