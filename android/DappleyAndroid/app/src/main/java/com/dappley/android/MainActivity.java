package com.dappley.android;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dappley.android.adapter.WalletListAdapter;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.widget.EmptyView;
import com.dappley.android.window.DeleteMenuWindow;
import com.google.zxing.activity.CaptureActivity;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import deadline.swiperecyclerview.SwipeRecyclerView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView;

    DeleteMenuWindow menuWindow;
    WalletListAdapter adapter;
    List<Wallet> wallets;
    Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();

        loadData();

        registerBroadcast();
    }

    private void initView() {
        adapter = new WalletListAdapter(this);
        adapter.addOnItemClickListener(itemClickListener);
        swipeRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        swipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);

        swipeRecyclerView.getSwipeRefreshLayout().setColorSchemeResources(R.color.colorPrimary);
        swipeRecyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRecyclerView.complete();
            }

            @Override
            public void onLoadMore() {

            }
        });
        swipeRecyclerView.setLoadMoreEnable(false);
        swipeRecyclerView.setEmptyView(EmptyView.get(this));
    }

    @OnClick(R.id.btn_qrcode)
    void qrCode() {
        startQrCode();
    }

    @OnClick(R.id.btn_add)
    void addWallet() {
        startActivity(new Intent(this, WalletAddActivity.class));
    }

    private void loadData() {
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

    private void readData() {
        List<Wallet> wallets = new ArrayList<>();
        try {
            List<String> addresses = StorageUtil.getAddresses();
            if (addresses == null) {
                addresses = new ArrayList<>(1);
            }
            Wallet wallet;
            for (String address : addresses) {
                wallet = new Wallet();
                wallet.setAddress(address);
                wallets.add(wallet);
            }
        } catch (IOException e) {
            Toast.makeText(this, R.string.note_read_failed, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        adapter.setList(wallets);

        this.wallets = wallets;

        loadBalance();
    }

    private void loadBalance() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wallets = Dappley.getWalletBalances(wallets);
                    handler.sendEmptyMessage(Constant.MSG_HOME_LIST);
                } catch (Exception e) {
                    handler.sendEmptyMessage(Constant.MSG_HOME_LIST_ERROR);
                    Log.e(TAG, "run: ", e);
                }

            }
        }).start();
    }

    private void deleteWallet(String address) {
        if (CommonUtil.isNull(address)) {
            return;
        }
        StorageUtil.deleteAddress(address);

        Dappley.removeAddress(address);

        loadData();
    }

    private void startQrCode() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_ACT_QR_CODE);
    }

    private WalletListAdapter.OnItemClickListener itemClickListener = new WalletListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            wallet = wallets.get(position);
            Intent intent = new Intent(MainActivity.this, WalletDetailActivity.class);
            intent.putExtra("wallet", wallet);
            startActivity(intent);
        }

        @Override
        public void onLongClick(View view, int position) {
            wallet = wallets.get(position);
            View rootView = ((ViewGroup) (getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
            if (menuWindow == null) {
                menuWindow = new DeleteMenuWindow(MainActivity.this, new DeleteMenuWindow.OnWindowClickListener() {
                    @Override
                    public void onDelClick() {
                        deleteWallet(wallet.getAddress());
                    }
                });
            }
            menuWindow.showPopupWindow(rootView);
        }
    };

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROAD_WALLET_LIST_UPDATE);
        registerReceiver(receiver, filter);
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constant.BROAD_WALLET_LIST_UPDATE.equals(intent.getAction())) {
                loadData();
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readData();
                } else {
                    Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
                }
                break;
            case Constant.REQ_PERM_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startQrCode();
                } else {
                    Toast.makeText(MainActivity.this, R.string.note_permittion_camera, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQ_ACT_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(com.google.zxing.util.Constant.INTENT_EXTRA_KEY_QR_SCAN);
            System.out.println("scan:" + scanResult);

            if (scanResult != null && scanResult.length() > 0) {
                Intent intent = new Intent(MainActivity.this, TransferActivity.class);
                intent.putExtra("toAddress", scanResult);
                startActivity(intent);
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_HOME_LIST) {
                adapter.setList(wallets);
            } else if (msg.what == Constant.MSG_HOME_LIST_ERROR) {
                Toast.makeText(MainActivity.this, R.string.note_node_error, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
