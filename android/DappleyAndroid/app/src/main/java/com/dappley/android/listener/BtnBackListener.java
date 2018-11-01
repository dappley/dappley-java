package com.dappley.android.listener;

import android.app.Activity;
import android.view.View;

public class BtnBackListener implements View.OnClickListener {
    private Activity activity;

    public BtnBackListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.finish();
    }
}
