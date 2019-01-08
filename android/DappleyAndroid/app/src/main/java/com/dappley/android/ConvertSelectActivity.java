package com.dappley.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.adapter.WalletSelectListAdapter;
import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.dialog.WalletPasswordDialog;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.java.core.po.Wallet;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.widget.DividerListItemDecoration;
import com.dappley.android.widget.EmptyView;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import deadline.swiperecyclerview.SwipeRecyclerView;

public class ConvertSelectActivity extends AppCompatActivity {
    private static final String TAG = "ConvertSelectActivity";
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView;

    WalletPasswordDialog walletPasswordDialog;

    WalletSelectListAdapter adapter;
    List<Wallet> wallets;
    Wallet wallet;
    int selectedIndex = -1;

    BigInteger baseValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_select);

        ButterKnife.bind(this);

        initData();

        initView();

        loadData();
    }

    private void initView() {
        tvTitle.setText(R.string.title_select_address);
        btnBack.setOnClickListener(new BtnBackListener(this));

        adapter = new WalletSelectListAdapter(this, baseValue);
        adapter.addOnItemClickListener(itemClickListener);
        swipeRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        swipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);
        swipeRecyclerView.getRecyclerView().addItemDecoration(new DividerListItemDecoration(this));
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
        swipeRecyclerView.setEmptyView(EmptyView.get(this));
    }

    private void initData() {
        Intent intent = getIntent();
        baseValue = (BigInteger) intent.getSerializableExtra("baseFee");
        if (baseValue == null) {
            baseValue = BigInteger.ZERO;
        }
    }

    @OnClick(R.id.btn_confirm)
    void confirm() {
        if (wallets == null || wallets.size() == 0) {
            Toast.makeText(this, R.string.note_no_valid_wallet, Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedIndex < 0) {
            Toast.makeText(this, R.string.note_select_address, Toast.LENGTH_SHORT).show();
            return;
        }
        wallet = wallets.get(selectedIndex);
        if (wallet == null || wallet.getBalance() == null || wallet.getBalance().compareTo(baseValue) < 0) {
            Toast.makeText(this, R.string.note_select_address, Toast.LENGTH_SHORT).show();
            return;
        }

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

    public void loadData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return;
        }
        readData();
    }

    public void readData() {
        List<Wallet> wallets = null;
        try {
            wallets = StorageUtil.getWallets(this);
            if (wallets == null) {
                wallets = new ArrayList<>(1);
            }
        } catch (IOException e) {
            Toast.makeText(this, R.string.note_read_failed, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "readData: ", e);
        }
        adapter.setList(wallets);

        this.wallets = wallets;

        swipeRecyclerView.setRefreshing(false);

        loadBalance();
    }

    private void loadBalance() {
        LoadingDialog.show(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wallets = Dappley.getWalletBalances(wallets);
                    handler.sendEmptyMessage(Constant.MSG_WALLET_SELECT_LIST);
                } catch (Exception e) {
                    handler.sendEmptyMessage(Constant.MSG_WALLET_SELECT_ERROR);
                    Log.e(TAG, "run: ", e);
                }

            }
        }).start();
    }

    private void onPasswordInput(String password) {
        if (password.length() == 0) {
            Toast.makeText(this, R.string.note_no_password, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Wallet wallet = Dappley.decryptWallet(this.wallet, password);
            if (wallet == null || wallet.getPrivateKey() == null) {
                Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
                return;
            }
            this.wallet = wallet;

            walletPasswordDialog.close();

            Intent intent = new Intent();
            intent.putExtra("wallet", this.wallet);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "onPasswordInput: ", e);
            Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private WalletSelectListAdapter.OnItemClickListener itemClickListener = new WalletSelectListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            selectedIndex = position;
            adapter.setSelectedIndex(selectedIndex);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                readData();
                break;
            default:
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_WALLET_SELECT_LIST) {
                adapter.setList(wallets);
                LoadingDialog.close();
            } else if (msg.what == Constant.MSG_WALLET_SELECT_ERROR) {
                Toast.makeText(ConvertSelectActivity.this, R.string.note_node_error, Toast.LENGTH_SHORT).show();
                LoadingDialog.close();
            }
        }
    };
}
