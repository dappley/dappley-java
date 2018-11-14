package com.dappley.android.sdk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.dappley.android.sdk.task.LocalBlockSchedule;

public class LocalBlockService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocalBlockSchedule.stop();
        // start schedule
        LocalBlockSchedule.start(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LocalBlockSchedule.stop();
        super.onDestroy();
    }
}
