package com.dappley.android.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dappley.android.MainActivity;
import com.dappley.android.R;
import com.dappley.android.TransferActivity;
import com.dappley.android.WalletAddActivity;
import com.dappley.android.WalletDetailActivity;
import com.dappley.android.adapter.WalletListAdapter;
import com.dappley.android.sdk.Dappley;
import com.dappley.java.core.po.Wallet;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.widget.EmptyView;
import com.dappley.android.window.WalletMenuWindow;
import com.google.zxing.activity.CaptureActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import deadline.swiperecyclerview.SwipeRecyclerView;

public class WalletFragment extends Fragment {
    private static final String TAG = "WalletFragment";
    private static final int TASK_INIT_DELAY = 2;
    private static final int TASK_PERIOD = 8;

    ScheduledExecutorService schedule;
    ScheduledFuture future;

    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView;

    WalletMenuWindow menuWindow;
    WalletListAdapter adapter;
    List<Wallet> wallets;
    Wallet wallet;

    public WalletFragment() {
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
    }

    @Override
    public void onStop() {
        super.onStop();

        stopSchedule();
    }

    private void initView() {
        adapter = new WalletListAdapter(getActivity());
        adapter.addOnItemClickListener(itemClickListener);
        swipeRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        swipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);

        swipeRecyclerView.getSwipeRefreshLayout().setColorSchemeResources(R.color.colorPrimary);
        swipeRecyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        swipeRecyclerView.setLoadMoreEnable(false);
        swipeRecyclerView.setEmptyView(EmptyView.get(getActivity()));
    }

    @OnClick(R.id.btn_qrcode)
    void qrCode() {
        startQrCode();
    }

    @OnClick(R.id.btn_add)
    void addWallet() {
        startActivity(new Intent(getActivity(), WalletAddActivity.class));
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
        adapter.setList(wallets);

        this.wallets = wallets;

        loadBalance();
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

    private WalletListAdapter.OnItemClickListener itemClickListener = new WalletListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            if (position >= wallets.size()) {
                return;
            }
            wallet = wallets.get(position);
            Intent intent = new Intent(getActivity(), WalletDetailActivity.class);
            intent.putExtra("wallet", wallet);
            startActivity(intent);
        }

        @Override
        public void onLongClick(View view, int position) {
            if (position >= wallets.size()) {
                return;
            }
            wallet = wallets.get(position);
            View rootView = ((ViewGroup) (getActivity().getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
            if (menuWindow == null) {
                menuWindow = new WalletMenuWindow(getActivity(), new WalletMenuWindow.OnWindowClickListener() {
                    @Override
                    public void onCopyClick() {
                        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("data", wallet.getAddress());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getActivity(), R.string.note_wallet_copied, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDelClick() {
                        deleteWallet(wallet.getAddress());
                    }
                });
            }
            menuWindow.showPopupWindow(rootView);
        }
    };

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
            if (msg.what == Constant.MSG_HOME_LIST) {
                adapter.setList(wallets);
                swipeRecyclerView.complete();
            } else if (msg.what == Constant.MSG_HOME_LIST_ERROR) {
                Toast.makeText(getActivity(), R.string.note_node_error, Toast.LENGTH_SHORT).show();
                swipeRecyclerView.complete();
            } else if (msg.what == Constant.MSG_HOME_LIST_BREAK) {
                swipeRecyclerView.complete();
            }
        }
    };

    class DataThread extends Thread {
        @Override
        public void run() {
            try {
                if (wallets == null || wallets.size() == 0) {
                    handler.sendEmptyMessage(Constant.MSG_HOME_LIST_BREAK);
                    return;
                }
                wallets = Dappley.getWalletBalances(wallets);
                handler.sendEmptyMessage(Constant.MSG_HOME_LIST);
            } catch (Exception e) {
                handler.sendEmptyMessage(Constant.MSG_HOME_LIST_ERROR);
                Log.e(TAG, "run: ", e);
            }
            Log.d(TAG, "DataThread run once");
        }
    }
}
