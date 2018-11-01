package com.dappley.android.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.dappley.android.R;

public class EmptyView {

    public static View get(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, null);
        return view;
    }
}
