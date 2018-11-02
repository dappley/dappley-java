package com.dappley.android.util;

import android.widget.EditText;

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
}
