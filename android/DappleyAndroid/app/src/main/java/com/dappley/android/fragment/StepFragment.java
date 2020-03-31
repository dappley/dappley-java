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
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.ConvertSelectActivity;
import com.dappley.android.R;
import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.network.RetrofitRequest;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.DuplicateUtil;
import com.dappley.android.util.PreferenceUtil;
import com.dappley.android.util.StorageUtil;
import com.dappley.google.step.GoogleStep;
import com.dappley.java.core.po.SendTxResult;
import com.dappley.java.core.po.Wallet;
import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.TodayStepManager;
import com.today.step.lib.TodayStepService;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
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
    GoogleStep googleStep;

    @BindView(R.id.swipe_fresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.txt_step_all)
    TextView tvStepAll;
    @BindView(R.id.txt_step_converted)
    TextView tvStepConverted;
    @BindView(R.id.txt_step_rest)
    TextView tvStepRest;
    @BindString(R.string.note_convert_success)
    String successString;

    String contractAddressStep = Constant.ADDRESS_STEP_CONTRACT;
    String contract;
    int todayStep;
    int convertedStep;
    int restStep;
    String today;

    boolean isGoogleSupported;
    boolean isStepLibUsed;
    int unLoginCounter;

    StepHandler handler;

    public StepFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new StepHandler(this);

        TodayStepManager.init(getActivity().getApplication());

        loadStepCounter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int newStep = getNewStep();

                        Message message = handler.obtainMessage(Constant.MSG_STEP_REFRESH);
                        message.arg1 = newStep;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });

        return view;
    }

    private void registerStepLib() {
        Intent intent = new Intent(getActivity(), TodayStepService.class);
        getActivity().startService(intent);

        getActivity().bindService(intent, stepServiceConnection, Context.BIND_AUTO_CREATE);

        isStepLibUsed = true;
    }

    private void readTodayConverted() {
        Date now = new Date();
        today = CommonUtil.formatDate(now, "yyyy-MM-dd");
        convertedStep = getTodayConvertedSteps(today);
        tvStepConverted.setText("" + convertedStep);
    }

    @OnClick(R.id.btn_convert)
    void convertCoin() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
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
        if (isStepLibUsed) {
            getActivity().unbindService(stepServiceConnection);
        }
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    public void loadStepCounter() {
        boolean isNativeStep = PreferenceUtil.getBoolean(getContext(), Constant.PREF_NATIVE_STEP, true);
        if (isNativeStep) {
            // use native step counter
            isGoogleSupported = false;
            registerStepLib();
        } else {
            // try to user google-fit's data
            if (googleStep == null) {
                googleStep = new GoogleStep(getActivity());
            }
            isGoogleSupported = true;
            if (isStepLibUsed) {
                getActivity().unbindService(stepServiceConnection);
            }
        }
    }

    public void onAddressSelected(final Wallet wallet) {
        if (wallet == null || wallet.getAddress() == null || wallet.getAddress().length() == 0) {
            Toast.makeText(getActivity(), R.string.note_select_address, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.show(getActivity());

        requestContractAddress(wallet);
    }

    private void requestContractAddress(final Wallet wallet) {
        RetrofitRequest.sendGetRequest(Constant.URL_CONTRACT_ADDRESS, new RetrofitRequest.ResultHandler(getActivity()) {
            @Override
            public void onBeforeResult() {
            }

            @Override
            public void onResult(String response) {
                if (response == null || response.trim().length() == 0 || response.trim().replaceAll("\n", "").length() == 0) {
                    Toast.makeText(getActivity(), R.string.note_remote_contract_empty, Toast.LENGTH_SHORT).show();
                    LoadingDialog.close();
                    return;
                }
                response = response.trim().replaceAll("\n", "");
                boolean isContract = Dappley.validateContractAddress(response);
                if (!isContract) {
                    Toast.makeText(getActivity(), R.string.note_remote_contract_invalid, Toast.LENGTH_SHORT).show();
                    LoadingDialog.close();
                    return;
                }
                contractAddressStep = response;

                // step convert to 10E+9 coin
                BigDecimal convertValue = new BigDecimal(String.valueOf(restStep)).multiply(com.dappley.java.core.util.Constant.COIN_DW);
                contract = String.format(Constant.STEP_CONTRACT, wallet.getAddress(), convertValue.toPlainString());

                requestGasCountAndPrice(wallet);
            }

            @Override
            public void onAfterFailure() {
                LoadingDialog.close();
            }
        });
    }

    private void requestGasCountAndPrice(final Wallet wallet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BigInteger gasLimit = Dappley.estimateGas(wallet.getAddress(), wallet.getPrivateKey(), contractAddressStep, contract);
                    BigInteger gasPrice = Dappley.getGasPrice();
                    toConvert(wallet, gasLimit, gasPrice);
                } catch (Exception e) {
                    SendTxResult sendTxResult = new SendTxResult();
                    Message msg = handler.obtainMessage(Constant.MSG_CONVERT_FINISH);
                    msg.obj = sendTxResult;
                    handler.sendMessage(msg);
                    Log.e(TAG, "requestGasCountAndPrice: ", e);
                }
            }
        }).start();
    }

    private void toConvert(final Wallet wallet, final BigInteger gasLimit, final BigInteger gasPrice) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendTxResult sendTxResult = null;
                try {
                    BigInteger tip = BigInteger.ZERO;
                    sendTxResult = Dappley.sendTransactionWithContract(wallet.getAddress(), contractAddressStep, baseFee, wallet.getPrivateKey(), tip, gasLimit, gasPrice, contract);
                } catch (Exception e) {
                    Log.e(TAG, "sendTransactionWithContract: ", e);
                }
                Message msg = handler.obtainMessage(Constant.MSG_CONVERT_FINISH);
                msg.obj = sendTxResult;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void onConvertFinish(SendTxResult sendTxResult) {
        LoadingDialog.close();
        if (sendTxResult != null && sendTxResult.getCode() == SendTxResult.CODE_SUCCESS) {
            String note = String.format(successString, restStep);
            Toast.makeText(getActivity(), note, Toast.LENGTH_SHORT).show();

            try {
                StorageUtil.saveStep(getActivity(), today, restStep);
            } catch (IOException e) {
            }
            restStep = 0;
            tvStepRest.setText("0");
            readTodayConverted();
        } else {
            Toast.makeText(getActivity(), R.string.note_convert_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private ServiceConnection stepServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSportStepInterface = ISportStepInterface.Stub.asInterface(service);

            if (isGoogleSupported) {
                return;
            }
            int newStep = getNewStep();
            // initialize restStep
            restStep = newStep - convertedStep;
            if (restStep < 0) {
                restStep = 0;
            }
            updateAll(newStep);
            startUpdateRestAnimator(newStep);

            startSchedule();
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
        future = schedule.scheduleWithFixedDelay(new StepRunnable(), TASK_INIT_DELAY, TASK_PERIOD, TimeUnit.SECONDS);

        Log.d(TAG, "startSchedule: step view updater started.");
    }

    public void stopSchedule() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);

            Log.d(TAG, "stopSchedule: step view updater stopped.");
        }
    }

    private void afterRefresh(int newStep) {
        updateAll(newStep);
        startUpdateRestAnimator(newStep);
        refreshLayout.setRefreshing(false);
    }

    private int getNewStep() {
        int newStep = 0;
        if (isGoogleSupported) {
            return googleStep.getStep();
        }
        if (iSportStepInterface != null) {
            try {
                newStep = iSportStepInterface.getCurrentTimeSportStep();
            } catch (RemoteException e) {
            }
        }
        return newStep;
    }

    private void updateAll(int newStep) {
        todayStep = newStep;
        if (newStep <= 0) {
            todayStep = 0;
        }
        Message msg = handler.obtainMessage(Constant.MSG_STEP_ALL);
        msg.arg1 = todayStep;
        handler.sendMessage(msg);
    }

    private void startUpdateRestAnimator(final int newStep) {
        if (newStep <= 0) {
            Message msg = handler.obtainMessage(Constant.MSG_STEP_UPDATE);
            msg.arg1 = 0;
            handler.sendMessage(msg);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = restStep;
                int diff = todayStep - convertedStep - restStep;
                if (diff < 0) {
                    return;
                }
                int segment = (int) diff / 10;
                if (segment < 1) {
                    segment = 1;
                }
                restStep += diff;
                while (true) {
                    Message msg = handler.obtainMessage(Constant.MSG_STEP_UPDATE);
                    if (i > restStep) {
                        i = restStep;
                    }
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    if (i >= restStep) {
                        break;
                    }
                    i += segment;
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    private int getTodayConvertedSteps(String today) {
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
            Log.e(TAG, "getTodayConvertedSteps: ", e);
        }
        return 0;
    }

    class StepRunnable implements Runnable {
        @Override
        public void run() {
            int newStep = getNewStep();
            updateAll(newStep);
            startUpdateRestAnimator(newStep);
            Log.d(TAG, "StepThread run once: " + newStep);
        }
    }

    static class StepHandler extends Handler {
        WeakReference<StepFragment> weakReference;

        public StepHandler(StepFragment stepFragment) {
            weakReference = new WeakReference<>(stepFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            StepFragment stepFragment = weakReference.get();
            if (stepFragment == null) {
                return;
            }
            if (msg.what == Constant.MSG_STEP_UPDATE && stepFragment.tvStepAll != null) {
                stepFragment.tvStepRest.setText("" + msg.arg1);
            } else if (msg.what == Constant.MSG_CONVERT_FINISH) {
                SendTxResult sendTxResult = (SendTxResult) msg.obj;
                stepFragment.onConvertFinish(sendTxResult);
            } else if (msg.what == Constant.MSG_STEP_ALL) {
                int stepAll = msg.arg1;
                if (stepAll < 0) {
                    stepFragment.tvStepAll.setText("-");
                } else {
                    stepFragment.tvStepAll.setText("" + stepAll);
                }
            } else if (msg.what == Constant.MSG_STEP_REFRESH) {
                stepFragment.afterRefresh(msg.arg1);
            }
        }
    }
}
