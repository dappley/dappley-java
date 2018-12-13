package com.dappley.android.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.CrashHandler;

public class AppData extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // register dappley service
        Dappley.init(getApplicationContext(), Dappley.DataMode.REMOTE_ONLINE);

        CrashHandler.getInstance().init(getApplicationContext());
    }
}
