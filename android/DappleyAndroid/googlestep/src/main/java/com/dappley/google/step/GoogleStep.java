package com.dappley.google.step;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.HistoryClient;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Google step api.
 */
public class GoogleStep {
    private static final String TAG = "FitApi";
    public static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 8001;

    private Activity activity;
    private HistoryClient client;
    private ExecutorService executor;

    private FitnessOptions fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build();

    public GoogleStep(Activity activity) {
        this.activity = activity;
        executor = Executors.newFixedThreadPool(3);
        if (GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(activity), fitnessOptions)) {
            client = Fitness.getHistoryClient(activity, GoogleSignIn.getLastSignedInAccount(activity));
        }
    }

    /**
     * Check is google fitness service supported.
     * @return boolean true/false
     */
    public boolean isSupported() {
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(activity), fitnessOptions)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Request user's permission of google service
     * @param activity
     */
    public void requestPermissions(Activity activity) {
        GoogleSignIn.requestPermissions(activity, GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, GoogleSignIn.getLastSignedInAccount(activity),
                fitnessOptions);
    }

    /**
     * Read step of today.
     * @return int total steps in day
     */
    public int getStep() {
        try {
            if (client == null) {
                client = Fitness.getHistoryClient(activity, GoogleSignIn.getLastSignedInAccount(activity));
            }
            ReadStepThread readStepThread = new ReadStepThread(activity);
            FutureTask<Integer> futureTask = new FutureTask<>(readStepThread);
            executor.submit(futureTask);
            Integer steps = futureTask.get();
            return steps;
        } catch (Exception e) {
            Log.e(TAG, "getStep: ", e);
        }
        return 0;
    }

    /**
     * Thread used to read step data from google fit api.
     */
    class ReadStepThread implements Callable<Integer> {
        Activity activity;

        public ReadStepThread(Activity activity) {
            this.activity = activity;
        }

        @Override
        public Integer call() {
            try {
                Task<DataSet> response = client.readDailyTotalFromLocalDevice(DataType.TYPE_STEP_COUNT_DELTA);
                DataSet totalSet = Tasks.await(response, 5, TimeUnit.SECONDS);
                if (totalSet == null) {
                    return 0;
                }
                if (totalSet.isEmpty()) {
                    return 0;
                }
                List<DataPoint> dataPoints = totalSet.getDataPoints();
                if (dataPoints == null || dataPoints.size() == 0) {
                    return 0;
                }
                DataPoint dataPoint = dataPoints.get(0);
                if (dataPoint == null) {
                    return 0;
                }
                Value value = dataPoint.getValue(Field.FIELD_STEPS);
                if (value == null) {
                    return 0;
                }
                return value.asInt();
            } catch (Exception e) {
                Log.e(TAG, "call: ", e);
            }
            return 0;
        }
    }
}
