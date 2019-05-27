package com.dappley.java.core.util;

import java.math.BigDecimal;

/**
 * System constants definition.
 */
public class Constant {
    /**
     * wallet address checksum length
     */
    public static final int ADDRESS_CHECKSUM_LENGTH = 4;
    /**
     * basic string encoding charset
     */
    public static final String CHARSET_UTF_8 = "UTF-8";
    /**
     * coin scale
     */
    public static final int COIN_SCALE = 9;
    /**
     * DW unit
     */
    public static final BigDecimal COIN_DW = new BigDecimal(String.valueOf(Math.pow(10, COIN_SCALE)));
}
