package com.dappley.java.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

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
            return getDefaultZero(Constant.COIN_SCALE);
        }
        try {
            BigDecimal divided = new BigDecimal(balance).divide(Constant.COIN_DW).setScale(Constant.COIN_SCALE, RoundingMode.DOWN);
            return divided.toPlainString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDefaultZero(Constant.COIN_SCALE);
    }

    /**
     * Returns formatted balance in DW unit mode, only has 2 points
     * @param balance wallet balance
     * @return String formatted string
     */
    public static String getDwWith2Points(BigInteger balance) {
        if (balance == null || balance.compareTo(BigInteger.ZERO) <= 0) {
            return getDefaultZero(2);
        }
        try {
            BigDecimal divided = new BigDecimal(balance).divide(Constant.COIN_DW).setScale(2, RoundingMode.DOWN);
            return divided.toPlainString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDefaultZero(2);
    }

    /**
     * Returns 0 with certain bits
     * @param bits
     * @return String long zero with point
     */
    private static String getDefaultZero(int bits) {
        StringBuffer sb = new StringBuffer("0.");
        for (int i = 0; i < bits; i++) {
            sb.append("0");
        }
        if (sb.length() == 2) {
            sb.append("00");
        }
        return sb.toString();
    }
}
