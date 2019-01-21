package com.dappley.android.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dappley.android.MainActivity;
import com.dappley.android.R;
import com.dappley.android.TransferActivity;
import com.dappley.android.WalletAddActivity;
import com.dappley.android.WalletDetailActivity;
import com.dappley.android.WalletReceiveCodeActivity;
import com.dappley.android.adapter.WalletHomePagerAdapter;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.window.WalletMenuWindow;
import com.dappley.java.core.po.Wallet;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.zxing.activity.CaptureActivity;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletFragment extends Fragment {
    private static final String TAG = "WalletFragment";
    private static final int TASK_INIT_DELAY = 2;
    private static final int TASK_PERIOD = 8;

    ScheduledExecutorService schedule;
    ScheduledFuture future;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.cycle_viewpager)
    HorizontalInfiniteCycleViewPager cycleViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    WalletMenuWindow menuWindow;
    WalletHomePagerAdapter pagerAdapter;
    List<Wallet> wallets;
    int currentIndex = 0;

    public WalletFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this, view);

        initView();

        loadData();

        loadBalance();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        startSchedule();
    }

    @Override
    public void onStop() {
        super.onStop();

        stopSchedule();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
            layoutParams.topMargin = CommonUtil.getStatusBarHeight(getActivity());
            toolbar.setLayoutParams(layoutParams);
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();

                loadBalance();
            }
        });
        pagerAdapter = new WalletHomePagerAdapter(getActivity(), cycleViewPager);
        cycleViewPager.setAdapter(pagerAdapter);
        cycleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                refreshLayout.setRefreshing(true);
                loadBalance();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick(R.id.btn_qrcode)
    void qrCode() {
        startQrCode();
    }

    @OnClick(R.id.btn_add)
    void addWallet() {
        startActivity(new Intent(getActivity(), WalletAddActivity.class));
    }

    @OnClick(R.id.linear_receive)
    void receive() {
        Wallet wallet = getCurrentWallet();
        if (wallet == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), WalletReceiveCodeActivity.class);
        intent.putExtra("wallet", wallet);
        startActivity(intent);
    }

    @OnClick(R.id.linear_transfer)
    void transfer() {
        Wallet wallet = getCurrentWallet();
        if (wallet == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), TransferActivity.class);
        intent.putExtra("wallet", wallet);
        startActivity(intent);
    }

    @OnClick(R.id.linear_balance)
    void balance() {
        Wallet wallet = getCurrentWallet();
        if (wallet == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), WalletDetailActivity.class);
        intent.putExtra("wallet", wallet);
        intent.putExtra("position", currentIndex);
        startActivity(intent);
    }

    @OnClick(R.id.linear_scan)
    void scan() {
        startQrCode();
    }

    @OnClick(R.id.linear_copy)
    void copy() {
        Wallet wallet = getCurrentWallet();
        if (wallet == null) {
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("data", wallet.getAddress());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getActivity(), R.string.note_wallet_copied, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.linear_delete)
    void delete() {
        Wallet wallet = getCurrentWallet();
        if (wallet == null) {
            return;
        }
        deleteWallet(wallet.getAddress());
    }

    private Wallet getCurrentWallet() {
        if (wallets == null || wallets.size() == 0) {
            Toast.makeText(getActivity(), R.string.note_no_valid_wallet, Toast.LENGTH_SHORT).show();
            return null;
        }
        Wallet wallet = wallets.get(currentIndex);
        return wallet;
    }

    public void loadData() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity.checkReadPermission()) {
            readData();
        }
    }

    public void readData() {
        List<Wallet> wallets = null;
        try {
            wallets = StorageUtil.getWallets(getActivity());
            if (wallets == null) {
                wallets = new ArrayList<>(1);
            }
        } catch (IOException e) {
            Log.e(TAG, "readData: ", e);
            Toast.makeText(getActivity(), R.string.note_read_failed, Toast.LENGTH_SHORT).show();
        }
        if (!CommonUtil.walletsEquals(this.wallets, wallets)) {
            pagerAdapter.setList(wallets);
            this.wallets = wallets;
        }
    }

    private void loadBalance() {
        new DataThread().start();
    }

    private void deleteWallet(String address) {
        if (CommonUtil.isNull(address)) {
            return;
        }
        StorageUtil.deleteAddress(getActivity(), address);

        Dappley.removeAddress(address);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadChangedData();
    }

    public void startQrCode() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (!mainActivity.checkCameraPermission()) {
            return;
        }
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        getActivity().startActivityForResult(intent, Constant.REQ_ACT_QR_CODE);
    }

    public void onScanResult(Intent data) {
        Bundle bundle = data.getExtras();
        String scanResult = bundle.getString(com.google.zxing.util.Constant.INTENT_EXTRA_KEY_QR_SCAN);

        if (scanResult != null && scanResult.length() > 0) {
            Intent intent = new Intent(getActivity(), TransferActivity.class);
            intent.putExtra("toAddress", scanResult);
            startActivity(intent);
        }
    }

    private void startSchedule() {
        stopSchedule();

        if (schedule == null) {
            schedule = Executors.newScheduledThreadPool(1);
        }
        future = schedule.scheduleAtFixedRate(new DataThread(), TASK_INIT_DELAY, TASK_PERIOD, TimeUnit.SECONDS);

        Log.d(TAG, "startSchedule: walletFrag data sync started.");
    }

    public void stopSchedule() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);

            Log.d(TAG, "stopSchedule: walletFrag data sync stopped.");
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_HOME_BALANCE) {
                pagerAdapter.updateCurrent(msg.arg1, (BigInteger) msg.obj);
                refreshLayout.setRefreshing(false);
            } else if (msg.what == Constant.MSG_HOME_BALANCE_ERROR) {
                Toast.makeText(getActivity(), R.string.note_node_error, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            } else if (msg.what == Constant.MSG_HOME_BALANCE_BREAK) {
                refreshLayout.setRefreshing(false);
            }
        }
    };

    class DataThread extends Thread {
        @Override
        public void run() {
            Message message = new Message();
            try {
                if (wallets == null || wallets.size() == 0) {
                    message.what = Constant.MSG_HOME_BALANCE_BREAK;
                    handler.sendMessage(message);
                    return;
                }
                int position = currentIndex;
                Wallet wallet = wallets.get(position);
                BigInteger balance = Dappley.getWalletBalance(wallet.getAddress());
                message.arg1 = position;
                message.obj = balance;
                message.what = Constant.MSG_HOME_BALANCE;
                handler.sendMessage(message);
            } catch (Exception e) {
                message.what = Constant.MSG_HOME_BALANCE_ERROR;
                handler.sendMessage(message);
                Log.e(TAG, "run: ", e);
            }
            Log.d(TAG, "DataThread run once");
        }
    }
}
