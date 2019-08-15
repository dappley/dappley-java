package com.dappley.android.util;

public class DuplicateUtil {
    private static long lastClickTime;
    private static final long TIME_INTERVAL = 500L;

    public static boolean dupClickCheck() {
        long now = System.currentTimeMillis();
        if ((now - lastClickTime) < TIME_INTERVAL) {
            lastClickTime = now;
            return true;
        }
        lastClickTime = now;
        return false;
    }
}
