package com.dappley.android.util;

import android.app.Activity;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    public static boolean isNull(String str) {
        return (str == null || str.length() == 0) ? true : false;
    }

    public static boolean isNull(EditText editText) {
        if (editText == null) {
            return true;
        }
        String value = editText.getText().toString();
        if (value == null || value.length() == 0 || value.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static String getNotNullString(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return str;
    }

    public static Date dateAdd(Date time, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.DAY_OF_MONTH, interval);
        return cal.getTime();
    }

    public static String formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        if (activity != null) {
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
