package com.dappley.android.util;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

public class EventUtil {

    public static void handleSoftInput(MotionEvent event, Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (activity.getCurrentFocus() != null) {
                if (activity.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }
}
