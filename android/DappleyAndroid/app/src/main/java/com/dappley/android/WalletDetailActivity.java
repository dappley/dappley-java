package com.dappley.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.adapter.UtxoListAdapter;
import com.dappley.android.dialog.WalletPasswordDialog;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.Constant;
import com.dappley.android.widget.EmptyView;
import com.dappley.java.core.po.Utxo;
import com.dappley.java.core.po.Wallet;
import com.dappley.java.core.util.CoinUtil;

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
import deadline.swiperecyclerview.SwipeRecyclerView;

public class WalletDetailActivity extends AppCompatActivity {
    private static final String TAG = "WalletDetailActivity";
    private static int[] ICONS = new int[]{R.drawable.icon_wallet_1, R.drawable.icon_wallet_2, R.drawable.icon_wallet_3, R.drawable.icon_wallet_4};
    private static final int TASK_INIT_DELAY = 5;
    private static final int TASK_PERIOD = 10;

    ScheduledExecutorService schedule;
    ScheduledFuture future;

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.txt_name)
    TextView tvName;
    @BindView(R.id.txt_address)
    TextView tvAddress;
    @BindView(R.id.txt_value)
    TextView tvValue;
    @BindView(R.id.icon_wallet)
    ImageView imgIcon;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView;

    WalletPasswordDialog walletPasswordDialog;

    UtxoListAdapter adapter;
    List<Utxo> utxos;
    Wallet wallet;
    BigInteger balance;
    int pageIndex = 1;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);

        ButterKnife.bind(this);

        initView();

        initData();

        loadData();
    }

    private void initView() {
        tvTitle.setText(R.string.title_wallet);
        btnBack.setOnClickListener(new BtnBackListener(this));

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                loadData();
            }
        });

        adapter = new UtxoListAdapter(this);
        adapter.addOnItemClickListener(itemClickListener);
        swipeRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        swipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);

        swipeRecyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                loadData();
            }
        });
        swipeRecyclerView.setRefreshEnable(false);
        swipeRecyclerView.setEmptyView(EmptyView.get(this));

        refreshLayout.setRefreshing(true);
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");
        position = intent.getIntExtra("position", 0);
        utxos = new ArrayList<>();

        tvName.setText(wallet.getName());
        tvAddress.setText(wallet.getAddress());
        if (position > 0) {
            imgIcon.setImageResource(ICONS[(position - 1) % ICONS.length]);
        }
    }

    @OnClick(R.id.btn_receive)
    void receive() {
        Intent intent = new Intent(this, WalletReceiveCodeActivity.class);
        intent.putExtra("wallet", wallet);
        startActivity(intent);
    }

    @OnClick(R.id.btn_export)
    void export() {
        if (walletPasswordDialog == null) {
            walletPasswordDialog = new WalletPasswordDialog(this, new WalletPasswordDialog.OnClickListener() {
                @Override
                public void onConfirm(String password) {
                    onPasswordInput(password);
                }
            });
        }
        walletPasswordDialog.show();
    }

    @OnClick(R.id.btn_transfer)
    void transfer() {
        Intent intent = new Intent(this, TransferActivity.class);
        intent.putExtra("wallet", wallet);
        startActivity(intent);
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

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loadBalance();
                    loadUtxoList();
                    handler.sendEmptyMessage(Constant.MSG_WALLET_DETAIL);
                } catch (Exception e) {
                    handler.sendEmptyMessage(Constant.MSG_WALLET_DETAIL_ERROR);
                    Log.e(TAG, "run: ", e);
                }
            }
        }).start();
    }

    private void loadBalance() {
        balance = Dappley.getWalletBalance(wallet.getAddress());
    }

    private void loadUtxoList() {
        List<Utxo> utxos = Dappley.getUtxos(wallet.getAddress(), pageIndex, Constant.PAGE_SIZE);
        if (pageIndex == 1) {
            if (utxos == null || utxos.size() == 0) {
                this.utxos.clear();
            } else {
                this.utxos = utxos;
            }
        } else {
            if (utxos == null || utxos.size() == 0) {
                pageIndex--;
            } else {
                this.utxos.addAll(utxos);
            }
        }
    }

    private void onPasswordInput(String password) {
        if (password.length() == 0) {
            Toast.makeText(WalletDetailActivity.this, R.string.note_no_password, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Wallet wallet = Dappley.decryptWallet(this.wallet, password);
            if (wallet == null || wallet.getPrivateKey() == null) {
                Toast.makeText(WalletDetailActivity.this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
                return;
            }
            WalletDetailActivity.this.wallet = wallet;

            walletPasswordDialog.close();
            Intent intent = new Intent(WalletDetailActivity.this, WalletExportActivity.class);
            intent.putExtra("wallet", wallet);
            startActivity(intent);
        } catch (Exception e) {
            Log.i(TAG, "onPasswordInput: ", e);
            Toast.makeText(WalletDetailActivity.this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
        }
    }

    private UtxoListAdapter.OnItemClickListener itemClickListener = new UtxoListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {

        }
    };

    private void startSchedule() {
        stopSchedule();

        if (schedule == null) {
            schedule = Executors.newScheduledThreadPool(1);
        }
        future = schedule.scheduleAtFixedRate(new DataThread(), TASK_INIT_DELAY, TASK_PERIOD, TimeUnit.SECONDS);

        Log.d(TAG, "startSchedule: walletDetail data sync started.");
    }

    public void stopSchedule() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);

            Log.d(TAG, "stopSchedule: walletDetail data sync stopped.");
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_WALLET_DETAIL) {
                tvValue.setText(CoinUtil.getDw(balance));
                adapter.setList(utxos);

                refreshLayout.setRefreshing(false);
                swipeRecyclerView.complete();
            } else if (msg.what == Constant.MSG_WALLET_DETAIL_ERROR) {
                Toast.makeText(WalletDetailActivity.this, R.string.note_node_error, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
                swipeRecyclerView.complete();
            } else if (msg.what == Constant.MSG_WALLET_DETAIL_REFRESH) {
                tvValue.setText(CoinUtil.getDw(balance));
            }
        }
    };

    class DataThread extends Thread {
        @Override
        public void run() {
            try {
                loadBalance();
                handler.sendEmptyMessage(Constant.MSG_WALLET_DETAIL_REFRESH);
            } catch (Exception e) {
                handler.sendEmptyMessage(Constant.MSG_WALLET_DETAIL_ERROR);
            }
            Log.d(TAG, "DataThread run once");
        }
    }
}
