package com.dappley.android.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dappley.android.MainActivity;
import com.dappley.android.R;
import com.dappley.android.TransferActivity;
import com.dappley.android.WalletAddActivity;
import com.dappley.android.WalletDetailActivity;
import com.dappley.android.WalletReceiveCodeActivity;
import com.dappley.android.adapter.WalletHomePagerAdapter;
import com.dappley.android.dialog.ConfirmDialog;
import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.network.RetrofitRequest;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.DuplicateUtil;
import com.dappley.android.util.PreferenceUtil;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.window.WalletMenuWindow;
import com.dappley.java.core.po.Wallet;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
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
    @BindView(R.id.relative_pager)
    RelativeLayout relativePager;
    @BindView(R.id.linear_add_wallet)
    LinearLayout linearAddWallet;

    WalletMenuWindow menuWindow;
    WalletHomePagerAdapter pagerAdapter;
    List<Wallet> wallets;
    int currentIndex = 0;
    boolean isActivityActive = false;

    WalletHandler handler;

    public WalletFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new WalletHandler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this, view);

        initView();

        loadData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        startSchedule();

        isActivityActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();

        stopSchedule();

        isActivityActive = false;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
            layoutParams.topMargin = CommonUtil.getStatusBarHeight(getActivity());
            toolbar.setLayoutParams(layoutParams);

            layoutParams = (FrameLayout.LayoutParams) relativePager.getLayoutParams();
            layoutParams.topMargin += CommonUtil.getStatusBarHeight(getActivity());
            relativePager.setLayoutParams(layoutParams);
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
        cycleViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    refreshLayout.setEnabled(false);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    refreshLayout.setEnabled(true);
                }
                return false;
            }
        });
        cycleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = cycleViewPager.getRealItem();
                refreshLayout.setRefreshing(true);
                loadBalance();

                PreferenceUtil.setInt(getActivity(), Constant.PREF_CURRENT_WALLET, currentIndex);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @OnClick(R.id.btn_qrcode)
    void qrCode() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        Wallet wallet = getCurrentWallet();
        if (wallet == null) {
            return;
        }
        startQrCode(null);
    }

    @OnClick({R.id.btn_add, R.id.linear_add_wallet})
    void addWallet() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        startActivity(new Intent(getActivity(), WalletAddActivity.class));
    }

    @OnClick(R.id.linear_receive)
    void receive() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
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
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
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
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
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
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        Wallet wallet = getCurrentWallet();
        if (wallet == null) {
            return;
        }
        startQrCode(wallet);
    }

    @OnClick(R.id.linear_copy)
    void copy() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
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
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        final Wallet wallet = getCurrentWallet();
        if (wallet == null) {
            return;
        }
        ConfirmDialog confirmDialog = new ConfirmDialog(getActivity(), new ConfirmDialog.OnClickListener() {
            @Override
            public void onConfirm() {
                deleteWallet(wallet.getAddress());
            }
        });
        confirmDialog.setTitle(R.string.note_confirm_title);
        confirmDialog.setContent(R.string.note_confirm_delete_wallet);
        confirmDialog.show();
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
        } else {
            return;
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
            stopSchedule();
            if (wallets.size() == 0) {
                linearAddWallet.setVisibility(View.VISIBLE);
                cycleViewPager.setVisibility(View.GONE);
                PreferenceUtil.setInt(getActivity(), Constant.PREF_CURRENT_WALLET, 0);
                this.wallets = wallets;
                currentIndex = 0;
            } else {
                linearAddWallet.setVisibility(View.GONE);
                cycleViewPager.setVisibility(View.VISIBLE);
                pagerAdapter.setList(wallets);
                this.wallets = wallets;

                cycleViewPager.setAdapter(pagerAdapter);
                currentIndex = 0;
                PreferenceUtil.setInt(getActivity(), Constant.PREF_CURRENT_WALLET, currentIndex);
                if (isActivityActive) {
                    startSchedule();
                }
            }
        }
    }

    public void loadBalance() {
        new DataThread().start();
    }

    private void deleteWallet(String address) {
        if (CommonUtil.isNull(address)) {
            return;
        }

        StorageUtil.deleteAddress(getActivity(), address);

        Dappley.removeAddress(address);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.loadChangedData(Constant.REQ_WALLET_DELETE, null);
    }

    public void receiveReward(final String address) {
        ConfirmDialog confirmDialog = new ConfirmDialog(getActivity(), new ConfirmDialog.OnClickListener() {
            @Override
            public void onConfirm() {
                LoadingDialog.show(getActivity());
                requestReward(address);
            }
        });
        confirmDialog.setTitle(R.string.note_confirm_title);
        confirmDialog.setContent(R.string.note_receive_coin_content);
        confirmDialog.setConfirmText(R.string.layout_yes);
        confirmDialog.setCancelText(R.string.layout_no);
        confirmDialog.show();
    }

    private void requestReward(String address) {
        String url = String.format(Constant.URL_NEW_WALLET_REWARD, address);
        RetrofitRequest.sendGetRequest(url, new RetrofitRequest.ResultHandler(getActivity()) {
            @Override
            public void onBeforeResult() {
            }

            @Override
            public void onResult(String response) {
                if (response == null || response.trim().length() == 0) {
                    Toast.makeText(getActivity(), R.string.note_receive_coin_failed, Toast.LENGTH_SHORT).show();
                    LoadingDialog.close();
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("code") != 0) {
                        Toast.makeText(getActivity(), R.string.note_receive_coin_failed, Toast.LENGTH_SHORT).show();
                        LoadingDialog.close();
                        return;
                    }
                    Toast.makeText(getActivity(), R.string.note_receive_coin_success, Toast.LENGTH_SHORT).show();
                    LoadingDialog.close();
                } catch (JSONException e) {
                }
            }

            @Override
            public void onAfterFailure() {
                LoadingDialog.close();
            }
        });
    }

    public void startQrCode(Wallet wallet) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (!mainActivity.checkCameraPermission()) {
            return;
        }
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        if (wallet != null) {
            intent.putExtra("wallet", wallet);
        }
        getActivity().startActivityForResult(intent, Constant.REQ_ACT_QR_CODE);
    }

    public void onScanResult(Intent data) {
        Bundle bundle = data.getExtras();
        String scanResult = bundle.getString(com.google.zxing.util.Constant.INTENT_EXTRA_KEY_QR_SCAN);
        Wallet wallet = (Wallet) bundle.getSerializable("wallet");

        if (scanResult != null && scanResult.length() > 0) {
            Intent intent = new Intent(getActivity(), TransferActivity.class);
            intent.putExtra("toAddress", scanResult);
            intent.putExtra("wallet", wallet);
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

    class DataThread extends Thread {
        @Override
        public void run() {

            try {
                if (wallets == null || wallets.size() == 0) {
                    Thread.sleep(10);
                    Message message = handler.obtainMessage(Constant.MSG_HOME_BALANCE_BREAK);
                    handler.sendMessage(message);
                    return;
                }
                int position = currentIndex;
                if (position >= wallets.size()) {
                    return;
                }
                Wallet wallet = wallets.get(position);
                BigInteger balance = Dappley.getWalletBalance(wallet.getAddress());
                Message message = handler.obtainMessage(Constant.MSG_HOME_BALANCE);
                message.arg1 = position;
                message.obj = balance;
                handler.sendMessage(message);
            } catch (Exception e) {
                Message message = handler.obtainMessage(Constant.MSG_HOME_BALANCE_ERROR);
                handler.sendMessage(message);
                Log.e(TAG, "run: ", e);
            }
            Log.d(TAG, "DataThread run once");
        }
    }

    static class WalletHandler extends Handler {
        WeakReference<WalletFragment> weakReference;

        public WalletHandler(WalletFragment walletFragment) {
            weakReference = new WeakReference<WalletFragment>(walletFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            WalletFragment walletFragment = weakReference.get();
            if (walletFragment == null) {
                return;
            }
            if (msg.what == Constant.MSG_HOME_BALANCE) {
                int position = msg.arg1;
                walletFragment.pagerAdapter.updateCurrent(position, (BigInteger) msg.obj);
                walletFragment.refreshLayout.setRefreshing(false);
            } else if (msg.what == Constant.MSG_HOME_BALANCE_ERROR) {
                Toast.makeText(walletFragment.getActivity(), R.string.note_node_error, Toast.LENGTH_SHORT).show();
                walletFragment.refreshLayout.setRefreshing(false);
            } else if (msg.what == Constant.MSG_HOME_BALANCE_BREAK) {
                walletFragment.refreshLayout.setRefreshing(false);
            }
        }
    }
}
