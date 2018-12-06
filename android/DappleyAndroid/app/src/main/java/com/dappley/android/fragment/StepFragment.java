package com.dappley.android.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.ConvertSelectActivity;
import com.dappley.android.R;
import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.TodayStepManager;
import com.today.step.lib.TodayStepService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepFragment extends Fragment {
    private static final String TAG = "StepFragment";
    private static final int TASK_INIT_DELAY = 3;
    private static final int TASK_PERIOD = 3;
    private static final BigInteger baseFee = new BigInteger("1");

    ISportStepInterface iSportStepInterface;
    ScheduledExecutorService schedule;
    ScheduledFuture future;

    @BindView(R.id.swipe_fresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.txt_step)
    TextView tvStep;
    @BindView(R.id.txt_step_converted)
    TextView tvStepConverted;
    @BindView(R.id.btn_convert)
    Button btnConvert;
    @BindString(R.string.note_convert_success)
    String successString;

    int todayStep;
    int convertedStep;
    int stepDiff;
    String today;

    public StepFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TodayStepManager.init(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        Intent intent = new Intent(getActivity(), TodayStepService.class);
        getActivity().startService(intent);

        getActivity().bindService(intent, stepServiceConnection, Context.BIND_AUTO_CREATE);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                todayStep = 0;
                int newStep = getNewStep();
                startUpdateAnimator(newStep);
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void readTodayConverted() {
        Date now = new Date();
        today = CommonUtil.formatDate(now, "yyyy-MM-dd");
        convertedStep = getTodayConvertedSteps(today);
        tvStepConverted.setText("" + convertedStep);
    }

    @OnClick(R.id.btn_convert)
    void convertCoin() {
        if (todayStep <= 0 || todayStep <= convertedStep) {
            Toast.makeText(getActivity(), R.string.note_no_step, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(), ConvertSelectActivity.class);
        intent.putExtra("baseFee", baseFee);
        getActivity().startActivityForResult(intent, Constant.REQ_ACT_CONVERT_SELECT);
    }

    @Override
    public void onStart() {
        super.onStart();

        readTodayConverted();

        startSchedule();
    }

    @Override
    public void onStop() {
        super.onStop();

        stopSchedule();
    }

    @Override
    public void onDetach() {
        getActivity().unbindService(stepServiceConnection);
        super.onDetach();
    }

    public void onAddressSeleted(final Wallet wallet) {
        if (wallet == null || wallet.getAddress() == null || wallet.getAddress().length() == 0) {
            Toast.makeText(getActivity(), R.string.note_select_address, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.show(getActivity());

        stepDiff = todayStep - convertedStep;
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = false;
                try {
                    String contract = "{\"function\":\"record\",\"args\":[\"%s\",\"%d\"]}";
                    contract = String.format(contract, wallet.getAddress(), stepDiff);
                    isSuccess = Dappley.sendTransactionWithContract(wallet.getAddress(), Constant.ADDRESS_STEP_CONTRACT, baseFee, wallet.getPrivateKey(), contract);
                } catch (Exception e) {
                    Log.e(TAG, "sendTransactionWithContract: ", e);
                }
                Message msg = new Message();
                msg.obj = isSuccess;
                msg.what = Constant.MSG_CONVERT_FINISH;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void onConvertFinish(boolean isSuccess) {
        LoadingDialog.close();
        if (isSuccess) {
            String note = String.format(successString, stepDiff);
            Toast.makeText(getActivity(), note, Toast.LENGTH_SHORT).show();

            try {
                StorageUtil.saveStep(getActivity(), today, stepDiff);
            } catch (IOException e) {
            }
            stepDiff = 0;
            readTodayConverted();
        } else {
            Toast.makeText(getActivity(), R.string.note_convert_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private ServiceConnection stepServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSportStepInterface = ISportStepInterface.Stub.asInterface(service);

            int newStep = getNewStep();
            startUpdateAnimator(newStep);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private void startSchedule() {
        stopSchedule();

        if (schedule == null) {
            schedule = Executors.newScheduledThreadPool(1);
        }
        future = schedule.scheduleAtFixedRate(new StepThread(), TASK_INIT_DELAY, TASK_PERIOD, TimeUnit.SECONDS);

        Log.d(TAG, "startSchedule: step view updater started.");
    }

    public void stopSchedule() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);

            Log.d(TAG, "stopSchedule: step view updater stopped.");
        }
    }

    private int getNewStep() {
        int newStep = 0;
        if (iSportStepInterface != null) {
            try {
                newStep = iSportStepInterface.getCurrentTimeSportStep();
            } catch (RemoteException e) {
            }
        }
        return newStep;
    }

    private void startUpdateAnimator(final int newStep) {
        if (newStep <= 0) {
            tvStep.setText("0");
            return;
        }
        if (newStep == todayStep) {
            return;
        }
        if (newStep < todayStep) {
            todayStep = 0;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = todayStep;
                int diff = newStep - todayStep;
                int segment = (int) diff / 10;
                if (segment < 1) {
                    segment = 1;
                }
                while (true) {
                    Message msg = new Message();
                    msg.what = Constant.MSG_STEP_UPDATE;
                    if (i > newStep) {
                        i = newStep;
                    }
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    if (i >= newStep) {
                        break;
                    }
                    i += segment;
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                    }
                }
                todayStep = newStep;
            }
        }).start();
    }

    public int getTodayConvertedSteps(String today) {
        try {
            List<Integer> steps = StorageUtil.getSteps(getActivity(), today);
            if (steps == null || steps.size() == 0) {
                return 0;
            }
            int total = 0;
            for (Integer integer : steps) {
                if (integer != null) {
                    total += integer;
                }
            }
            return total;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == Constant.MSG_STEP_UPDATE && tvStep != null) {
                tvStep.setText("" + msg.arg1);
            } else if (msg.what == Constant.MSG_CONVERT_FINISH) {
                boolean isSuccess = (boolean) msg.obj;
                onConvertFinish(isSuccess);
            }
        }
    };

    class StepThread extends Thread {
        @Override
        public void run() {
            int newStep = getNewStep();
            startUpdateAnimator(newStep);
            Log.d(TAG, "StepThread run once: " + newStep);
        }
    }
}
