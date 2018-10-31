package com.dappley.android.sdk.task;

import android.content.Context;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Block scheduled task.
 */
public class LocalBlockSchedule {
    private static final int TASK_INIT_DELAY = 0;
    private static final int TASK_PERIOD = 10;

    private static ScheduledExecutorService schedule;
    private static ScheduledFuture future;

    /**
     * start task
     */
    public static void start(Context context) {
        stop();
        if (schedule == null) {
            schedule = Executors.newScheduledThreadPool(1);
        }
        future = schedule.scheduleAtFixedRate(new LocalBlockThread(context), TASK_INIT_DELAY, TASK_PERIOD, TimeUnit.SECONDS);
    }

    /**
     * stop scheduled task
     */
    public static void stop() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);
        }
    }

}
