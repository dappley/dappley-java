package com.dappley.android.listener;

import android.content.Context;
import android.content.Intent;

import com.dappley.android.MainActivity;
import com.today.step.lib.BaseClickBroadcast;

/**
 * Step notification click event
 */
public class StepNotificationReceiver extends BaseClickBroadcast {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
    }
}
